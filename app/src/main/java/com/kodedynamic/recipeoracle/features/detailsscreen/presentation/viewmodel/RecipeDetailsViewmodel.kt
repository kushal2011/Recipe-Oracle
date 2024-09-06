package com.kodedynamic.recipeoracle.features.detailsscreen.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.kodedynamic.recipeoracle.apis.domain.models.RecipeModel
import com.kodedynamic.recipeoracle.apis.domain.usecase.GetRecipeByIdUseCase
import com.kodedynamic.recipeoracle.common.BundleKeys
import com.kodedynamic.recipeoracle.common.Empty
import com.kodedynamic.recipeoracle.common.ScreenEvent
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
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(RecipeDetailsState())
    val state: Flow<RecipeDetailsState> get() = _state

    private val recipeDetails: String =
        savedStateHandle.get<String>(BundleKeys.RECIPE_DETAILS) ?: String.Empty

    private val recipeId: String =
        savedStateHandle.get<String>(BundleKeys.RECIPE_ID) ?: String.Empty

    init {
        if (recipeDetails.isNotEmpty()) {
            val gson = Gson()
            _state.update {
                it.copy(
                    recipeData = gson.fromJson(recipeDetails, RecipeModel::class.java)
                )
            }
        } else if (recipeId.isNotEmpty()) {
            getRecipeDetails(recipeId)
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