package com.kdtech.recipeoracle.features.homescreen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdtech.recipeoracle.apis.ConfigManager
import com.kdtech.recipeoracle.coroutines.DispatcherProvider
import com.kdtech.recipeoracle.apis.domain.models.RecipeRequestModel
import com.kdtech.recipeoracle.apis.domain.usecase.GetHomeFeedUseCase
import com.kdtech.recipeoracle.apis.domain.usecase.GetRecipeUseCase
import com.kdtech.recipeoracle.common.BundleKeys
import com.kdtech.recipeoracle.features.homescreen.presentation.models.HomeState
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
class HomeViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val navigator: ScreenNavigator,
    private val getRecipesUseCase: GetRecipeUseCase,
    private val getHomeFeedUseCase: GetHomeFeedUseCase,
    private val configManager: ConfigManager
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: Flow<HomeState> get() = _state

    init {
        getHomeFeedData()
    }
    fun onBackPress() {
        navigator.navigate(ScreenAction.goTo(screen = Screen.Back()))
    }

    fun onDetailsClick(recipeName: String) = viewModelScope.launch(dispatcher.main) {
        navigator.navigate(
            ScreenAction.goTo(
                screen = Screen.RecipeChat(),
                map = mapOf(
                    BundleKeys.RECIPE_NAME to recipeName
                )
            )
        )
    }
//    private fun getIngredientsData() = viewModelScope.launch(dispatcher.io) {
//        val generativeModel = GenerativeModel(
//            modelName = "gemini-1.5-flash",
//            apiKey = BuildConfig.GEMENI_API_KEY
//        )
//        val prompt = Prompts.getPromptForIngredients()
//        val response = generativeModel.generateContent(prompt)
//        val gson = Gson()
//        val listType = object : TypeToken<List<IngredientModel>>() {}.type
//        val ingredientsList: List<IngredientModel> = gson.fromJson(response.text, listType)
//        _state.update {
//            it.copy(
//                ingredientsList = ingredientsList
//            )
//        }
//    }

    private fun getHomeFeedData() = viewModelScope.launch(dispatcher.io) {
        val version = configManager.fetchHomeFeedVersion()
        getHomeFeedUseCase(version).fold(
            onSuccess = { homeFeedWidgetsModel ->
                _state.update {
                    it.copy(
                        homeFeedWidgets = homeFeedWidgetsModel.widgetsList
                    )
                }
            },
            onFailure = {
                // do nothing
            }
        )
    }

    private fun getRecipesData() = viewModelScope.launch(dispatcher.io) {
        getRecipesUseCase(
            param = RecipeRequestModel()
        ).fold(
            onSuccess = { recipes ->
                _state.update {
                    it.copy(
                        recipeList = recipes
                    )
                }
            },
            onFailure = {
                // do nothing
            }
        )
    }
}