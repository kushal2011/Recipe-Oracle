package com.kdtech.recipeoracle.features.homescreen.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kdtech.recipeoracle.BuildConfig
import com.kdtech.recipeoracle.coroutines.DispatcherProvider
import com.kdtech.recipeoracle.data.IngredientModel
import com.kdtech.recipeoracle.data.RecipeModel
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
        getRecipesData()
    }
    fun onBackPress() {
        navigator.navigate(ScreenAction.goTo(screen = Screen.Back()))
    }

    fun onDetailsClick() = viewModelScope.launch(dispatcher.main) {
        navigator.navigate(ScreenAction.goTo(screen = Screen.Details()))
    }
    private fun getIngredientsData() = viewModelScope.launch(dispatcher.io) {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.GEMENI_API_KEY
        )
        val prompt = Prompts.getPromptForIngredients()
        val response = generativeModel.generateContent(prompt)
        val gson = Gson()
        val listType = object : TypeToken<List<IngredientModel>>() {}.type
        val ingredientsList: List<IngredientModel> = gson.fromJson(response.text, listType)
        _state.update {
            it.copy(
                ingredientsList = ingredientsList
            )
        }
    }

    private fun getRecipesData() = viewModelScope.launch(dispatcher.io) {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.GEMENI_API_KEY
        )
        val prompt = Prompts.getPromptForRecipes()
        val response = generativeModel.generateContent(prompt)
        val gson = Gson()
        val listType = object : TypeToken<List<RecipeModel>>() {}.type
        val recipesList: List<RecipeModel> = gson.fromJson(response.text, listType)
        _state.update {
            it.copy(
                recipeList = recipesList
            )
        }
    }
}