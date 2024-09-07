package com.kodedynamic.recipeoracle.features.detailsscreen.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.Gson
import com.kodedynamic.recipeoracle.apis.domain.models.RecipeModel
import com.kodedynamic.recipeoracle.apis.domain.usecase.GetRecipeByIdUseCase
import com.kodedynamic.recipeoracle.common.BundleKeys
import com.kodedynamic.recipeoracle.common.Empty
import com.kodedynamic.recipeoracle.common.EventParams
import com.kodedynamic.recipeoracle.common.EventValues
import com.kodedynamic.recipeoracle.common.FirebaseEvents
import com.kodedynamic.recipeoracle.common.ScreenEvent
import com.kodedynamic.recipeoracle.common.ScreenNames
import com.kodedynamic.recipeoracle.coroutines.DispatcherProvider
import com.kodedynamic.recipeoracle.features.detailsscreen.presentation.models.RecipeDetailsState
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
class RecipeDetailsViewmodel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatcher: DispatcherProvider,
    private val navigator: ScreenNavigator,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val crashlytics: FirebaseCrashlytics
) : ViewModel() {
    private val _state = MutableStateFlow(RecipeDetailsState())
    val state: Flow<RecipeDetailsState> get() = _state

    private val recipeDetails: String =
        savedStateHandle.get<String>(BundleKeys.RECIPE_DETAILS) ?: String.Empty

    private val recipeId: String =
        savedStateHandle.get<String>(BundleKeys.RECIPE_ID) ?: String.Empty

    init {
        if (recipeDetails.isNotEmpty()) {
            logEvent(
                eventName = FirebaseEvents.ON_DETAILS_SCREEN_ENTERED,
                params = Bundle().apply {
                    putString(EventParams.SCREEN_NAME, ScreenNames.DETAILS_SCREEN)
                    putString(EventParams.ENTRY_FROM, EventValues.ENTRY_FROM_IN_APP)
                }
            )
            val gson = Gson()
            _state.update {
                it.copy(
                    recipeData = gson.fromJson(recipeDetails, RecipeModel::class.java)
                )
            }
        } else if (recipeId.isNotEmpty()) {
            logEvent(
                eventName = FirebaseEvents.ON_DETAILS_SCREEN_ENTERED,
                params = Bundle().apply {
                    putString(EventParams.SCREEN_NAME, ScreenNames.DETAILS_SCREEN)
                    putString(EventParams.ENTRY_FROM, EventValues.ENTRY_FROM_DEEPLINK)
                }
            )
            getRecipeDetails(recipeId)
        }
    }

    fun onBackPress() {
        navigator.navigate(ScreenAction.goTo(screen = Screen.Back()))
    }

    fun onChatClick(
        recipeName: String
    ) = viewModelScope.launch(dispatcher.main) {
        logEvent(
            eventName = FirebaseEvents.ON_CHAT_CLICKED,
            params = Bundle().apply {
                putString(EventParams.SCREEN_NAME, ScreenNames.DETAILS_SCREEN)
                putString(EventParams.RECIPE_NAME, recipeName)
            }
        )
        navigator.navigate(
            ScreenAction.goTo(
                screen = Screen.RecipeChat(),
                map = mapOf(
                    BundleKeys.RECIPE_NAME to recipeName
                )
            )
        )
    }

    fun getRecipeShareText(): String {
        val recipe = _state.value.recipeData
        logEvent(
            eventName = FirebaseEvents.ON_SHARE_RECIPE,
            params = Bundle().apply {
                putString(EventParams.SCREEN_NAME, ScreenNames.DETAILS_SCREEN)
                putString(EventParams.RECIPE_NAME, recipe?.recipeName)
                putString(EventParams.RECIPE_ID, recipe?.recipeId)
            }
        )
        return """
        🍽️ Recipe Name: ${recipe?.recipeName}
        🍴 Cuisine: ${recipe?.cuisineType}
        ⏳ Prep Time: ${recipe?.prepTime} minutes
        🥗 Health Rating: ${recipe?.healthRating}
        
        Discover the full recipe with ingredients and instructions here:
        https://recipeoracle.kodedynamic.com/recipe/${recipe?.recipeId}
    """.trimIndent()
    }

    private fun getRecipeDetails(
        recipeId: String
    ) = viewModelScope.launch(dispatcher.main) {
        _state.update { it.copy(isLoading = true) }
        getRecipeByIdUseCase(recipeId).fold(
            onSuccess = {
                _state.update { prev ->
                    prev.copy(
                        recipeData = it,
                        isLoading = false
                    )
                }
            },
            onFailure = {
                logCrashlyticsEvent("${ScreenNames.DETAILS_SCREEN} getRecipeDetails api failed with ${it.message}")
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