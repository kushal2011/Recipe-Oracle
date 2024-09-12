package com.kodedynamic.recipeoracle.features.recipechat.presentation.viewmodel

import android.os.Bundle
import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kodedynamic.recipeoracle.BuildConfig
import com.kodedynamic.recipeoracle.apis.ConfigManager
import com.kodedynamic.recipeoracle.apis.data.models.MessageDto
import com.kodedynamic.recipeoracle.apis.data.models.OpenAiChatRequestDto
import com.kodedynamic.recipeoracle.apis.data.repositories.NetworkRepository
import com.kodedynamic.recipeoracle.apis.domain.models.OpenAiChatModel
import com.kodedynamic.recipeoracle.apis.domain.usecase.ChatWithOpenAiUseCase
import com.kodedynamic.recipeoracle.common.BundleKeys
import com.kodedynamic.recipeoracle.common.ConnectivityStatus
import com.kodedynamic.recipeoracle.common.Empty
import com.kodedynamic.recipeoracle.common.EventParams
import com.kodedynamic.recipeoracle.common.EventValues
import com.kodedynamic.recipeoracle.common.FirebaseEvents
import com.kodedynamic.recipeoracle.common.ResourceProvider
import com.kodedynamic.recipeoracle.common.ScreenEvent
import com.kodedynamic.recipeoracle.common.ScreenNames
import com.kodedynamic.recipeoracle.coroutines.DispatcherProvider
import com.kodedynamic.recipeoracle.features.recipechat.presentation.mapper.MessageDtoMapper
import com.kodedynamic.recipeoracle.features.recipechat.presentation.models.MessageModel
import com.kodedynamic.recipeoracle.features.recipechat.presentation.models.RecipeChatState
import com.kodedynamic.recipeoracle.navigations.Screen
import com.kodedynamic.recipeoracle.navigations.ScreenAction
import com.kodedynamic.recipeoracle.navigations.ScreenNavigator
import com.kodedynamic.recipeoracle.prompt.Prompts
import com.kodedynamic.recipeoracle.resources.StringResources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val RETRY_AFTER = 2000L
private const val GEMINI_1_5_FLASH_MODEL_NAME = "gemini-1.5-flash"
private const val SYSTEM = "system"

@HiltViewModel
class RecipeChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatcher: DispatcherProvider,
    private val navigator: ScreenNavigator,
    private val networkRepository: NetworkRepository,
    private val resourcesProvider: ResourceProvider,
    private val configManager: ConfigManager,
    private val chatWithOpenAiUseCase: ChatWithOpenAiUseCase,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val crashlytics: FirebaseCrashlytics
) : ViewModel() {
    private val _state = MutableStateFlow(RecipeChatState())
    val state: Flow<RecipeChatState> get() = _state

    private val recipeName: String =
        savedStateHandle.get<String>(BundleKeys.RECIPE_NAME) ?: String.Empty
    private lateinit var chat: Chat

    private var userMessageCount: Int = 1
    private var hasConnectionBeenLost: Boolean = false
    private var isChatInitFailed: Boolean = false
    private var isChatFromGemini: Boolean = true
    private var entryFrom: String = String.Empty

    init {
        initChat()
    }

    private fun initChat() = viewModelScope.launch(dispatcher.main) {
        isChatFromGemini = configManager.fetchShouldUseGemini()
        entryFrom = if (recipeName.isNotEmpty()) {
            EventValues.ENTRY_FROM_DETAILS
        } else {
            EventValues.ENTRY_FROM_BOTTOM_BAR
        }
        logEvent(
            eventName = FirebaseEvents.CHAT_STARTED,
            params = Bundle().apply {
                putString(EventParams.SCREEN_NAME, ScreenNames.CHAT_SCREEN)
                putString(EventParams.RECIPE_NAME, recipeName)
                putString(EventParams.ENTRY_FROM, entryFrom)
            }
        )
        monitorNetworkConnection()
        startChat()
    }

    fun onBackPress() {
        navigator.navigate(ScreenAction.goTo(screen = Screen.Back()))
    }

    fun sendMessage(
        messageText: String
    ) = viewModelScope.launch(dispatcher.main) {
        if (_state.value.chatList.isNotEmpty()) {
            logEvent(
                eventName = FirebaseEvents.MESSAGE_SENT_BY_USER,
                params = Bundle().apply {
                    putString(EventParams.SCREEN_NAME, ScreenNames.CHAT_SCREEN)
                    putString(EventParams.RECIPE_NAME, recipeName)
                    putString(EventParams.ENTRY_FROM, entryFrom)
                    putInt(EventParams.MESSAGE_COUNT, userMessageCount)
                }
            )
            userMessageCount += 1
            _state.update {
                it.copy(
                    chatList = it.chatList.plus(MessageModel(messageText, true)),
                    typingIndicator = true
                )
            }
            if (isChatFromGemini) {
                val response = chat.sendMessage(messageText).text
                _state.update {
                    it.copy(
                        chatList = it.chatList.plus(MessageModel(response.toString(), false)),
                        typingIndicator = false
                    )
                }
            } else {
                val messageDtoMapper = MessageDtoMapper()
                val chatList = _state.value.chatList.mapIndexed { index, item ->
                    if (index == 0) {
                        MessageDto(
                            content = messageText,
                            role = SYSTEM
                        )
                    } else {
                        messageDtoMapper.map(item)
                    }
                }
                chatWithOpenAiUseCase(
                    OpenAiChatRequestDto(
                        model = "gpt-4o-2024-08-06", // to be taken from config
                        messages = chatList
                    )
                ).fold(
                    onSuccess = { response ->
                        openAiSendMsgSuccess(
                            response = response
                        )
                    },
                    onFailure = { e ->
                        logCrashlyticsEvent("${ScreenNames.CHAT_SCREEN} sendMessage (open ai) api failed with ${e.message}")
                        _state.update {
                            it.copy(
                                typingIndicator = false,
                                screenEvent = ScreenEvent.ShowToast(
                                    message = e.message.orEmpty(),
                                    resourceId = StringResources.somethingWentWrong
                                )
                            )
                        }
                    }
                )
            }

        } else {
            _state.update {
                it.copy(
                    screenEvent = ScreenEvent.ShowToast(
                        resourceId = StringResources.somethingWentWrong
                    )
                )
            }
        }
    }

    fun onScreenEventsShown() {
        _state.update { it.copy(screenEvent = ScreenEvent.None) }
    }

    private fun startChat(retryCount: Int = 3) = viewModelScope.launch(dispatcher.main) {
        _state.update {
            it.copy(
                typingIndicator = true
            )
        }

        var currentRetry = 0
        val prompt = Prompts.getPromptForChat(recipeName)

        while (currentRetry < retryCount) {
            if (isChatFromGemini) {
                val result = runCatching {
                    val generativeModel = GenerativeModel(
                        modelName = GEMINI_1_5_FLASH_MODEL_NAME,
                        apiKey = BuildConfig.GEMENI_API_KEY
                    )

                    chat = generativeModel.startChat()
                    chat.sendMessage(prompt)
                }
                result.onSuccess { firstAiMessage ->
                    startOnSuccessChatInit(firstAiMessage.text.toString())
                    return@launch
                }.onFailure { e ->
                    currentRetry++
                    if (currentRetry >= retryCount) {
                        startOnFailureChatInit(e.message.orEmpty())
                    } else {
                        logCrashlyticsEvent("${ScreenNames.CHAT_SCREEN} startChat api failed with ${e.message} in retry count: $currentRetry")
                        delay(RETRY_AFTER)
                    }
                }
            } else {
               chatWithOpenAiUseCase(
                   param = OpenAiChatRequestDto(
                       model = "gpt-4o-2024-08-06", // to be taken from config
                       messages = listOf(
                           MessageDto(
                           content = prompt,
                           role = SYSTEM
                       )
                       )
                   )
               ).fold(
                   onSuccess = { response ->
                       openAiSendMsgSuccess(
                           response = response,
                           isFromInit = true
                       )
                       return@launch
                   },
                   onFailure = {
                       isChatFromGemini = true
                       currentRetry++
                       delay(RETRY_AFTER)
                   }
               )
            }
        }
    }

    private fun openAiSendMsgSuccess(
        response: OpenAiChatModel,
        isFromInit: Boolean = false
    ) {
        val modelResponse = response.choices[0].messageModel?.content
        modelResponse?.let { res ->
            _state.update {
                it.copy(
                    chatList = it.chatList.plus(MessageModel(res, false)),
                    typingIndicator = false,
                    shouldEnableChat = true
                )
            }
            isChatInitFailed = false
        } ?: {
            logCrashlyticsEvent("${ScreenNames.CHAT_SCREEN} sendMessage (open ai) isInit: $isFromInit api Success but data empty with $response")
            if (isFromInit) {
                isChatFromGemini = true
                startChat()
            } else {
                _state.update {
                    it.copy(
                        typingIndicator = false,
                        screenEvent = ScreenEvent.ShowToast(
                            message = response.choices[0].finishReason,
                            resourceId = StringResources.somethingWentWrong
                        )
                    )
                }
            }
        }
    }

    private fun startOnFailureChatInit(
        errorMessage: String
    ) {
        logCrashlyticsEvent("${ScreenNames.CHAT_SCREEN} startChat api failed with $errorMessage and exhausted reties")
        _state.update {
            it.copy(
                typingIndicator = false,
                screenEvent = ScreenEvent.ShowToast(
                    message = errorMessage,
                    resourceId = StringResources.somethingWentWrong
                )
            )
        }
        isChatInitFailed = true
    }

    private fun startOnSuccessChatInit(
        text: String
    ) {
        _state.update {
            it.copy(
                chatList = it.chatList.plus(
                    MessageModel(
                        text,
                        false
                    )
                ),
                typingIndicator = false,
                shouldEnableChat = true
            )
        }
        isChatInitFailed = false
    }

    private fun monitorNetworkConnection() {
        viewModelScope.launch {
            networkRepository.getNetworkStatus().collectLatest { status ->
                val snackBarMessage = when (status) {
                    ConnectivityStatus.Lost, ConnectivityStatus.Unavailable -> {
                        hasConnectionBeenLost = true
                        StringResources.noInternetConnection to SnackbarDuration.Long
                    }

                    ConnectivityStatus.Losing -> {
                        hasConnectionBeenLost = true
                        StringResources.connectionUnstable to SnackbarDuration.Long
                    }

                    ConnectivityStatus.Available -> {
                        if (_state.value.chatList.isEmpty() && isChatInitFailed) {
                            startChat()
                        }
                        if (hasConnectionBeenLost) {
                            hasConnectionBeenLost = false
                            StringResources.connectionRestored to SnackbarDuration.Short
                        } else {
                            null
                        }
                    }
                }

                snackBarMessage?.let { (message, duration) ->
                    _state.update {
                        it.copy(
                            screenEvent = ScreenEvent.ShowSnackBar(
                                message = resourcesProvider.getString(message),
                                duration = duration
                            )
                        )
                    }
                }
            }
        }
    }

    private fun logEvent(eventName: String, params: Bundle) {
        firebaseAnalytics.logEvent(eventName, params)
    }

    private fun logCrashlyticsEvent(crashlyticsEvent: String) {
        crashlytics.recordException(Exception(crashlyticsEvent))
    }
}