package com.kdtech.recipeoracle.features.categoriesscreen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.kdtech.recipeoracle.coroutines.DispatcherProvider
import com.kdtech.recipeoracle.features.categoriesscreen.presentation.models.CategoriesState
import com.kdtech.recipeoracle.navigations.Screen
import com.kdtech.recipeoracle.navigations.ScreenAction
import com.kdtech.recipeoracle.navigations.ScreenNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val navigator: ScreenNavigator
): ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state: Flow<CategoriesState> get() = _state

    fun onBackPress() {
        navigator.navigate(ScreenAction.goTo(screen = Screen.Back()))
    }
}