package com.kodedynamic.recipeoracle.features.seeallscreen.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.Gson
import com.kodedynamic.recipeoracle.apis.domain.models.RecipeModel
import com.kodedynamic.recipeoracle.apis.domain.models.SeeAllRecipeRequest
import com.kodedynamic.recipeoracle.apis.domain.usecase.GetSeeAllRecipesUseCase
import com.kodedynamic.recipeoracle.common.BundleKeys
import com.kodedynamic.recipeoracle.common.EventParams
import com.kodedynamic.recipeoracle.common.FirebaseEvents
import com.kodedynamic.recipeoracle.common.ScreenEvent
import com.kodedynamic.recipeoracle.common.ScreenNames
import com.kodedynamic.recipeoracle.coroutines.DispatcherProvider
import com.kodedynamic.recipeoracle.features.seeallscreen.presentation.models.SeeAllState
import com.kodedynamic.recipeoracle.navigations.Screen
import com.kodedynamic.recipeoracle.navigations.ScreenAction
import com.kodedynamic.recipeoracle.navigations.ScreenNavigator
import com.kodedynamic.recipeoracle.resources.StringResources
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
    private val getSeeAllRecipesUseCase: GetSeeAllRecipesUseCase,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val crashlytics: FirebaseCrashlytics
): ViewModel() {
    private val _state = MutableStateFlow(SeeAllState())
    val state: Flow<SeeAllState> get() = _state

    private val screenTitle: String? = savedStateHandle.get<String>(BundleKeys.SCREEN_TITLE)
    private val cuisineType: String? = savedStateHandle.get<String>(BundleKeys.CUISINE_TYPE)
    private val prepTime: Int? = savedStateHandle.get<String>(BundleKeys.PREP_TIME)?.toIntOrNull()
    private val healthRating: Int? = savedStateHandle.get<String>(BundleKeys.HEALTH_RATING)?.toIntOrNull()
    private val topRated: Boolean? = savedStateHandle.get<String>(BundleKeys.TOP_RATED)?.toBoolean()

    init {
        _state.update { it.copy(screenTitle = screenTitle.orEmpty()) }
        getRecipes()
    }

    fun onBackPress() {
        navigator.navigate(ScreenAction.goTo(screen = Screen.Back()))
    }

    fun onScreenEventsShown() {
        _state.update { it.copy(screenEvent = ScreenEvent.None) }
    }

    fun onDetailsClick(
        recipeId: String,
    ) = viewModelScope.launch(dispatcher.main) {
        val recipeData: RecipeModel? = _state.value.recipes.find {
            it.recipeId == recipeId
        }

        if (recipeData != null) {
            logEvent(
                eventName = FirebaseEvents.ON_DETAILS_CLICKED,
                params = Bundle().apply {
                    putString(EventParams.SCREEN_NAME, ScreenNames.SEE_ALL_SCREEN)
                    putString(EventParams.RECIPE_NAME, recipeData.recipeName)
                    putString(EventParams.CUISINE_TYPE, recipeData.cuisineType)
                    putString(EventParams.RECIPE_ID, recipeData.recipeId)
                }
            )
            val gson = Gson()
            navigator.navigate(
                ScreenAction.goTo(
                    screen = Screen.Details(),
                    map = mapOf(
                        BundleKeys.RECIPE_DETAILS to gson.toJson(recipeData)
                    )
                )
            )
        } else {
            logCrashlyticsEvent("${ScreenNames.SEE_ALL_SCREEN} onDetailsClick else condition reached for recipeId: $recipeId")
        }
    }

    private fun getRecipes() = viewModelScope.launch(dispatcher.io) {
        _state.update { it.copy(isLoading = true) }
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
                        recipes = it,
                        isLoading = false
                    )
                }
            },
            onFailure = {
                logCrashlyticsEvent("${ScreenNames.SEE_ALL_SCREEN} getRecipes api failed with ${it.message}")
                _state.update { _prev ->
                    _prev.copy(
                        screenEvent = ScreenEvent.ShowToast(
                            message = it.message.orEmpty(),
                            resourceId = StringResources.somethingWentWrong
                        ),
                        isLoading = false
                    )
                }
            }
        )
    }

    private fun logEvent(eventName: String, params: Bundle) {
        firebaseAnalytics.logEvent(eventName, params)
    }

    private fun logCrashlyticsEvent(crashlyticsEvent: String) {
        crashlytics.recordException(Exception(crashlyticsEvent))
    }
}