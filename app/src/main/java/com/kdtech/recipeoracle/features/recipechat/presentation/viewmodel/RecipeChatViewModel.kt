package com.kdtech.recipeoracle.features.recipechat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.kdtech.recipeoracle.BuildConfig
import com.kdtech.recipeoracle.coroutines.DispatcherProvider
import com.kdtech.recipeoracle.features.recipechat.presentation.models.MessageModel
import com.kdtech.recipeoracle.features.recipechat.presentation.models.RecipeChatState
import com.kdtech.recipeoracle.navigations.Screen
import com.kdtech.recipeoracle.navigations.ScreenAction
import com.kdtech.recipeoracle.navigations.ScreenNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeChatViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val navigator: ScreenNavigator
): ViewModel() {
    private val _state = MutableStateFlow(RecipeChatState())
    val state: Flow<RecipeChatState> get() = _state

    private val dishName: String = "Pizza"
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
                chatList = it.chatList.plus(MessageModel(messageText, true))
            )
        }
      val response = chat.sendMessage(messageText)
        _state.update {
            it.copy(
                chatList = it.chatList.plus(MessageModel(response.text.toString(), false))
            )
        }
    }

    private fun startChat() = viewModelScope.launch(dispatcher.main) {
        val generativeModel = GenerativeModel(
            // The Gemini 1.5 models are versatile and work with multi-turn conversations (like chat)
            modelName = "gemini-1.5-flash",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = BuildConfig.GEMENI_API_KEY
        )

        chat = generativeModel.startChat()
        val firstAiMessage = chat.sendMessage("The user is currently viewing a recipe for $dishName. Your role is to engage the user in a helpful conversation about this recipe. Start by welcoming them and acknowledging the dish they're interested in. Offer assistance with any questions they might have about the recipe, ingredients, cooking techniques, or possible variations. Encourage them to interact by asking if they need tips or suggestions to make the dish perfect. Your response should be friendly, informative, and focused on enhancing their cooking experience.")
        _state.update {
            it.copy(
                chatList = it.chatList.plus(MessageModel(firstAiMessage.text.toString(), false))
            )
        }
    }
}