package com.kdtech.recipeoracle.features.detailsscreen.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.kdtech.recipeoracle.apis.domain.models.RecipeModel
import com.kdtech.recipeoracle.common.BundleKeys
import com.kdtech.recipeoracle.common.Empty
import com.kdtech.recipeoracle.coroutines.DispatcherProvider
import com.kdtech.recipeoracle.features.detailsscreen.presentation.models.RecipeDetailsState
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
class RecipeDetailsViewmodel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatcher: DispatcherProvider,
    private val navigator: ScreenNavigator
): ViewModel() {
    private val _state = MutableStateFlow(RecipeDetailsState())
    val state: Flow<RecipeDetailsState> get() = _state

    private val recipeDetails: String = savedStateHandle.get<String>(BundleKeys.RECIPE_DETAILS) ?: String.Empty

    init {
        val gson = Gson()
        _state.update {
            it.copy(
                recipeData = gson.fromJson(recipeDetails, RecipeModel::class.java)
            )
        }
    }

    fun onBackPress() {
        navigator.navigate(ScreenAction.goTo(screen = Screen.Back()))
    }

    fun onChatClick(
        recipeName: String
    ) = viewModelScope.launch(dispatcher.main) {
            navigator.navigate(
                ScreenAction.goTo(
                    screen = Screen.RecipeChat(),
                    map = mapOf(
                        BundleKeys.RECIPE_NAME to recipeName
                    )
                )
            )
    }

}