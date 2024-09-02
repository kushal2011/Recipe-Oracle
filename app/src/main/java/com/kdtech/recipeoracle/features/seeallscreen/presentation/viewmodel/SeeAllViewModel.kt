package com.kdtech.recipeoracle.features.seeallscreen.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.kdtech.recipeoracle.apis.domain.models.RecipeModel
import com.kdtech.recipeoracle.apis.domain.models.SeeAllRecipeRequest
import com.kdtech.recipeoracle.apis.domain.usecase.GetSeeAllRecipesUseCase
import com.kdtech.recipeoracle.common.BundleKeys
import com.kdtech.recipeoracle.coroutines.DispatcherProvider
import com.kdtech.recipeoracle.features.seeallscreen.presentation.models.SeeAllState
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
class SeeAllViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatcher: DispatcherProvider,
    private val navigator: ScreenNavigator,
    private val getSeeAllRecipesUseCase: GetSeeAllRecipesUseCase
): ViewModel() {
    private val _state = MutableStateFlow(SeeAllState())
    val state: Flow<SeeAllState> get() = _state

    private val cuisineType: String? = savedStateHandle.get<String>(BundleKeys.CUISINE_TYPE)
    private val prepTime: Int? = savedStateHandle.get<Int>(BundleKeys.PREP_TIME)
    private val healthRating: Int? = savedStateHandle.get<Int>(BundleKeys.HEALTH_RATING)
    private val topRated: Boolean? = savedStateHandle.get<Boolean>(BundleKeys.TOP_RATED)

    init {
        getRecipes()
    }

    private fun getRecipes() = viewModelScope.launch(dispatcher.io) {
        getSeeAllRecipesUseCase(
            SeeAllRecipeRequest(
                cuisineType = cuisineType,
                prepTime = prepTime,
                healthRating = healthRating,
                topRated = topRated
            )
        ).fold(
            onSuccess = {
                _state.update { prev ->
                    prev.copy(
                        recipes = it
                    )
                }
            },
            onFailure = {
                // do noting
            }
        )
    }

    fun onDetailsClick(
        recipeId: String,
    ) = viewModelScope.launch(dispatcher.main) {
        val recipeData: RecipeModel? = _state.value.recipes.find {
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
}