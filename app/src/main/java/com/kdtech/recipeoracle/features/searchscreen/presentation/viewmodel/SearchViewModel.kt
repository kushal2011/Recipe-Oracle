package com.kdtech.recipeoracle.features.searchscreen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.kdtech.recipeoracle.apis.domain.models.RecipeModel
import com.kdtech.recipeoracle.apis.domain.models.RecipeRequestModel
import com.kdtech.recipeoracle.apis.domain.usecase.GetRecipeUseCase
import com.kdtech.recipeoracle.apis.domain.usecase.GetSearchedRecipesUseCase
import com.kdtech.recipeoracle.common.BundleKeys
import com.kdtech.recipeoracle.common.Empty
import com.kdtech.recipeoracle.common.ScreenEvent
import com.kdtech.recipeoracle.coroutines.DispatcherProvider
import com.kdtech.recipeoracle.features.searchscreen.presentation.models.SearchState
import com.kdtech.recipeoracle.navigations.Screen
import com.kdtech.recipeoracle.navigations.ScreenAction
import com.kdtech.recipeoracle.navigations.ScreenNavigator
import com.kdtech.recipeoracle.resources.StringResources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val QUERY_DEBOUNCE = 500L

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val navigator: ScreenNavigator,
    private val searchUseCase: GetSearchedRecipesUseCase,
    private val getRecipesUseCase: GetRecipeUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state: Flow<SearchState> get() = _state

    val queryFlow: StateFlow<String> get() = _queryFlow.asStateFlow()
    private val _queryFlow = MutableStateFlow(String.Empty)

    init {
        observeSearchQuery()
    }

    fun onScreenEventsShown() {
        _state.update { it.copy(screenEvent = ScreenEvent.None) }
    }

    fun setQuery(query: String) {
        _queryFlow.update { query }
    }

    fun onCloseCLick() {
        setQuery(String.Empty)
        _state.update { it.copy(recipesList = emptyList()) }
    }
    fun onDetailsClick(
        recipeId: String,
    ) = viewModelScope.launch(dispatcher.main) {
        val recipeData: RecipeModel? = _state.value.recipesList.find {
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

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch(dispatcher.main) {
            queryFlow.debounce(QUERY_DEBOUNCE).collect {
                if (it.length > 2) {
                    searchRecipes(it)
                }
            }
        }
    }

    private fun searchRecipes(
        searchText: String
    ) = viewModelScope.launch(dispatcher.io) {
        _state.update { it.copy(isLoading = true) }
        searchUseCase(
            searchText
        ).fold(
            onSuccess = {
                if (it.isEmpty()) {
                    _state.update { prev ->
                        prev.copy(
                            screenEvent = ScreenEvent.ShowToast(
                                message = "This might take a moment. We’re gathering the freshest recipes for '${searchText}'.",
                            )
                        )
                    }
                    getRecipesData(searchText)
                } else {
                    _state.update { prev ->
                        prev.copy(
                            recipesList = it,
                            isLoading = false
                        )
                    }
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

    private fun getRecipesData(searchText: String) = viewModelScope.launch(dispatcher.io) {
        getRecipesUseCase(
            param = RecipeRequestModel(
                searchText = searchText
            )
        ).fold(
            onSuccess = { recipes ->
                _state.update { prev ->
                    prev.copy(
                        recipesList = recipes,
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