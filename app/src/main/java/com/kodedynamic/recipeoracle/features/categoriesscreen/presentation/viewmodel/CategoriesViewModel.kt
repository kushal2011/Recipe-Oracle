package com.kodedynamic.recipeoracle.features.categoriesscreen.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kodedynamic.recipeoracle.apis.ConfigManager
import com.kodedynamic.recipeoracle.apis.domain.usecase.GetCategoriesUseCase
import com.kodedynamic.recipeoracle.common.BundleKeys
import com.kodedynamic.recipeoracle.common.EventParams
import com.kodedynamic.recipeoracle.common.FirebaseEvents
import com.kodedynamic.recipeoracle.common.ScreenEvent
import com.kodedynamic.recipeoracle.common.ScreenNames
import com.kodedynamic.recipeoracle.coroutines.DispatcherProvider
import com.kodedynamic.recipeoracle.features.categoriesscreen.presentation.models.CategoriesState
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
class CategoriesViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val navigator: ScreenNavigator,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val configManager: ConfigManager,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val crashlytics: FirebaseCrashlytics
): ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state: Flow<CategoriesState> get() = _state

    init {
        getCuisineData()
    }

    fun onCuisineClick(cuisineType:String) = viewModelScope.launch(dispatcher.main) {
        logEvent(
            eventName = FirebaseEvents.ON_CUISINE_CLICKED,
            params = Bundle().apply {
                putString(EventParams.SCREEN_NAME, ScreenNames.CATEGORIES_SCREEN)
                putString(EventParams.CUISINE_TYPE, cuisineType)
            }
        )
        navigator.navigate(
            ScreenAction.goTo(
                screen = Screen.SeeAllRecipes(),
                map = mapOf(
                    BundleKeys.CUISINE_TYPE to cuisineType,
                    BundleKeys.SCREEN_TITLE to cuisineType
                )
            )
        )
    }

    fun onScreenEventsShown() {
        _state.update { it.copy(screenEvent = ScreenEvent.None) }
    }

    private fun getCuisineData() = viewModelScope.launch(dispatcher.io) {
        _state.update { it.copy(isLoading = true) }
        val version = configManager.fetchCategoriesVersion()
        getCategoriesUseCase(version).fold(
            onSuccess = {
                _state.update { _prev ->
                    _prev.copy(
                        cuisines = it.cuisines,
                        isLoading = false
                    )
                }
            },
            onFailure = {
                logCrashlyticsEvent("${ScreenNames.CATEGORIES_SCREEN} getCuisineData api failed with ${it.message}")
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