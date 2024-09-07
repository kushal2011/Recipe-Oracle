package com.kodedynamic.recipeoracle.features.homescreen.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.Gson
import com.kodedynamic.recipeoracle.apis.ConfigManager
import com.kodedynamic.recipeoracle.apis.data.models.ParamsDto
import com.kodedynamic.recipeoracle.apis.domain.models.RecipeModel
import com.kodedynamic.recipeoracle.coroutines.DispatcherProvider
import com.kodedynamic.recipeoracle.apis.domain.usecase.GetHomeFeedUseCase
import com.kodedynamic.recipeoracle.common.BundleKeys
import com.kodedynamic.recipeoracle.common.EventParams
import com.kodedynamic.recipeoracle.common.FirebaseEvents
import com.kodedynamic.recipeoracle.common.ScreenEvent
import com.kodedynamic.recipeoracle.common.ScreenNames
import com.kodedynamic.recipeoracle.features.homescreen.presentation.models.HomeState
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
class HomeViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val navigator: ScreenNavigator,
    private val getHomeFeedUseCase: GetHomeFeedUseCase,
    private val configManager: ConfigManager,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val crashlytics: FirebaseCrashlytics
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
            logEvent(
                eventName = FirebaseEvents.ON_SEE_ALL_CLICKED,
                params = Bundle().apply {
                    putString(EventParams.SCREEN_NAME, ScreenNames.HOME_SCREEN)
                    putString(EventParams.WIDGET_ID, widgetId)
                    putString(EventParams.TITLE, title)
                }
            )
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
        } else {
            logCrashlyticsEvent("${ScreenNames.HOME_SCREEN} onSeeAllClick else condition reached for widgetId: $widgetId")
            _state.update { _prev ->
                _prev.copy(
                    screenEvent = ScreenEvent.ShowToast(
                        resourceId = StringResources.somethingWentWrong
                    ),
                    isLoading = false
                )
            }
        }
    }

    fun onScreenEventsShown() {
        _state.update { it.copy(screenEvent = ScreenEvent.None) }
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
            logEvent(
                eventName = FirebaseEvents.ON_DETAILS_CLICKED,
                params = Bundle().apply {
                    putString(EventParams.SCREEN_NAME, ScreenNames.HOME_SCREEN)
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
            logCrashlyticsEvent("${ScreenNames.HOME_SCREEN} onDetailsClick else condition reached for recipeId: $recipeId & widgetId: $widgetId")
        }
    }

    private fun getHomeFeedData() = viewModelScope.launch(dispatcher.io) {
        _state.update { it.copy(isLoading = true) }
        val version = configManager.fetchHomeFeedVersion()
        getHomeFeedUseCase(version).fold(
            onSuccess = { homeFeedWidgetsModel ->
                _state.update {
                    it.copy(
                        homeFeedWidgets = homeFeedWidgetsModel.widgetsList,
                        isLoading = false
                    )
                }
            },
            onFailure = {
                logCrashlyticsEvent("${ScreenNames.HOME_SCREEN} getHomeFeedData api failed with ${it.message}")
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