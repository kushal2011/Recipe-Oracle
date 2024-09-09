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
import com.kodedynamic.recipeoracle.apis.data.repositories.NetworkRepository
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

@HiltViewModel
class RecipeChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatcher: DispatcherProvider,
    private val navigator: ScreenNavigator,
    private val networkRepository: NetworkRepository,
    private val resourcesProvider: ResourceProvider,
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
    private var entryFrom: String = String.Empty

    init {
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
        logEvent(
            eventName = FirebaseEvents.MESSAGE_SENT_BY_USER,
            params = Bundle().apply {
                putString(EventParams.SCREEN_NAME, ScreenNames.CHAT_SCREEN)
                putString(EventParams.RECIPE_NAME, recipeName)
                putString(EventParams.ENTRY_FROM, entryFrom)
                putInt(EventParams.MESSAGE_COUNT, userMessageCount)
            }
        )
        userMessageCount +=1
        _state.update {
            it.copy(
                chatList = it.chatList.plus(MessageModel(messageText, true)),
                typingIndicator = true
            )
        }
        val response = chat.sendMessage(messageText)
        _state.update {
            it.copy(
                chatList = it.chatList.plus(MessageModel(response.text.toString(), false)),
                typingIndicator = false
            )
        }
    }

    fun onScreenEventsShown() {
        _state.update { it.copy(screenEvent = ScreenEvent.None) }
    }

    private fun startChat(retryCount: Int = 1) = viewModelScope.launch(dispatcher.main) {
        _state.update {
            it.copy(
                typingIndicator = true
            )
        }

        var currentRetry = 0

        while (currentRetry < retryCount) {
            val result = runCatching {
                val generativeModel = GenerativeModel(
                    // The Gemini 1.5 models are versatile and work with multi-turn conversations (like chat)
                    modelName = "gemini-1.5-flash",
                    apiKey = BuildConfig.GEMENI_API_KEY
                )

                chat = generativeModel.startChat()
                chat.sendMessage(Prompts.getPromptForChat(recipeName))
            }

            result.onSuccess { firstAiMessage ->
                _state.update {
                    it.copy(
                        chatList = it.chatList.plus(
                            MessageModel(
                                firstAiMessage.text.toString(),
                                false
                            )
                        ),
                        typingIndicator = false
                    )
                }
                return@launch
            }.onFailure { e ->
                currentRetry++
                if (currentRetry >= retryCount) {
                    logCrashlyticsEvent("${ScreenNames.CHAT_SCREEN} startChat api failed with ${e.message} and exhausted reties")
                    _state.update {
                        it.copy(
                            typingIndicator = false,
                            screenEvent = ScreenEvent.ShowToast(
                                message = e.message.orEmpty(),
                                resourceId = StringResources.somethingWentWrong
                            )
                        )
                    }
                } else {
                    logCrashlyticsEvent("${ScreenNames.CHAT_SCREEN} startChat api failed with ${e.message} in retry count: $currentRetry")
                    delay(RETRY_AFTER)
                }
            }
        }
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