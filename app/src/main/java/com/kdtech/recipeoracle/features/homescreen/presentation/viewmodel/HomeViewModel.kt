package com.kdtech.recipeoracle.features.homescreen.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.kdtech.recipeoracle.BuildConfig
import com.kdtech.recipeoracle.coroutines.DispatcherProvider
import com.kdtech.recipeoracle.features.homescreen.presentation.models.HomeState
import com.kdtech.recipeoracle.navigations.Screen
import com.kdtech.recipeoracle.navigations.ScreenAction
import com.kdtech.recipeoracle.navigations.ScreenNavigator
import com.kdtech.recipeoracle.prompt.Prompts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val navigator: ScreenNavigator
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: Flow<HomeState> get() = _state

    init {
        getData()
    }
    fun onBackPress() {
        navigator.navigate(ScreenAction.goTo(screen = Screen.Back()))
    }

    fun onDetailsClick() = viewModelScope.launch(dispatcher.main) {
        navigator.navigate(ScreenAction.goTo(screen = Screen.Details()))
    }
    private fun getData() = viewModelScope.launch(dispatcher.io) {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.GEMENI_API_KEY
        )
        val prompt = Prompts.getPromptForRecipes()
        val response = generativeModel.generateContent(prompt)
        _state.update {
            it.copy(
                recipeText = response.text.orEmpty()
            )
        }
        Log.e("aaa","text: ${response.text.orEmpty()}")
        Log.e("aaa","size ${response.candidates.size} candidates: ${response.candidates[0]}")
        Log.e("aaa","functionCalls: ${response.functionCalls}")
        Log.e("aaa","functionResponse: ${response.functionResponse}")
        Log.e("aaa","promptFeedback: ${response.promptFeedback}")
        Log.e("aaa","usageMetadata total: ${response.usageMetadata?.totalTokenCount}")
        Log.e("aaa","usageMetadata prompt: ${response.usageMetadata?.promptTokenCount}")
    }
}