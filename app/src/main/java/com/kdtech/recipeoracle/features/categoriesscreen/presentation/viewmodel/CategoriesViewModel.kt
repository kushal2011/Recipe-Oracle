package com.kdtech.recipeoracle.features.categoriesscreen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdtech.recipeoracle.apis.domain.usecase.GetCategoriesUseCase
import com.kdtech.recipeoracle.common.BundleKeys
import com.kdtech.recipeoracle.coroutines.DispatcherProvider
import com.kdtech.recipeoracle.features.categoriesscreen.presentation.models.CategoriesState
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
                    BundleKeys.CUISINE_TYPE to cuisineType
                )
            )
        )
    }

    private fun getCuisineData() = viewModelScope.launch(dispatcher.io) {
        getCategoriesUseCase().fold(
            onSuccess = {
                _state.update { _prev ->
                    _prev.copy(
                        cuisines = it.cuisines
                    )
                }
            },
            onFailure = {
                // do nothing
            }
        )
    }
}