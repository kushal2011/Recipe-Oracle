package com.kodedynamic.recipeoracle.features.categoriesscreen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodedynamic.recipeoracle.apis.domain.usecase.GetCategoriesUseCase
import com.kodedynamic.recipeoracle.common.BundleKeys
import com.kodedynamic.recipeoracle.common.ScreenEvent
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
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state: Flow<CategoriesState> get() = _state

    init {
        getCuisineData()
    }

    fun onCuisineClick(cuisineType:String) = viewModelScope.launch(dispatcher.main) {
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
        getCategoriesUseCase().fold(
            onSuccess = {
                _state.update { _prev ->
                    _prev.copy(
                        cuisines = it.cuisines,
                        isLoading = false
                    )
                }
            },
            onFailure = {
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
}