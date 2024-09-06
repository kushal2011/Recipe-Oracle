package com.kodedynamic.recipeoracle.features.recipechat.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.kodedynamic.recipeoracle.BuildConfig
import com.kodedynamic.recipeoracle.common.BundleKeys
import com.kodedynamic.recipeoracle.common.Empty
import com.kodedynamic.recipeoracle.common.ScreenEvent
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val RETRY_AFTER = 2000L

@HiltViewModel
class RecipeChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatcher: DispatcherProvider,
    private val navigator: ScreenNavigator
) : ViewModel() {
    private val _state = MutableStateFlow(RecipeChatState())
    val state: Flow<RecipeChatState> get() = _state

    private val recipeName: String =
        savedStateHandle.get<String>(BundleKeys.RECIPE_NAME) ?: String.Empty
    private lateinit var chat: Chat

    init {
        startChat()
    }

    fun onBackPress() {
        navigator.navigate(ScreenAction.goTo(screen = Screen.Back()))
    }

    fun sendMessage(
        messageText: String
    ) = viewModelScope.launch(dispatcher.main) {
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
                    delay(RETRY_AFTER)
                }
            }
        }
    }

}