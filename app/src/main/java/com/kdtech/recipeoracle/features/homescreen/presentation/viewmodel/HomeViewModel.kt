package com.kdtech.recipeoracle.features.homescreen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.kdtech.recipeoracle.apis.ConfigManager
import com.kdtech.recipeoracle.apis.data.models.ParamsDto
import com.kdtech.recipeoracle.apis.domain.models.RecipeModel
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
    fun onSeeAllClick(
        widgetId: String,
        title: String
    ) = viewModelScope.launch(dispatcher.main) {
        val seeAllParams: ParamsDto? = _state.value.homeFeedWidgets.find {
            it.widgetId == widgetId
        }?.seeAll?.params
        if (seeAllParams != null) {
            val navigationParams = mutableMapOf<String, String>()

            seeAllParams.cuisineType?.let { navigationParams[BundleKeys.CUISINE_TYPE] = it }
            title.let { navigationParams[BundleKeys.SCREEN_TITLE] = it }
            seeAllParams.prepTime?.let { navigationParams[BundleKeys.PREP_TIME] = it.toString() }
            seeAllParams.healthRating?.let { navigationParams[BundleKeys.HEALTH_RATING] = it.toString() }
            seeAllParams.topRated?.let { navigationParams[BundleKeys.TOP_RATED] = it.toString() }
            navigator.navigate(
                ScreenAction.goTo(
                    screen = Screen.SeeAllRecipes(),
                    map = navigationParams
                )
            )
        }
    }

    fun onDetailsClick(
        recipeId: String,
        widgetId: String
    ) = viewModelScope.launch(dispatcher.main) {
        val recipeData: RecipeModel? = _state.value.homeFeedWidgets.find {
            it.widgetId == widgetId
        }?.recipesList?.find {
            it.recipeId == recipeId
        }

        if (recipeData != null) {
            val gson = Gson()
            navigator.navigate(
                ScreenAction.goTo(
                    screen = Screen.Details(),
                    map = mapOf(
                        BundleKeys.RECIPE_DETAILS to gson.toJson(recipeData)
                    )
                )
            )
        }
    }

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