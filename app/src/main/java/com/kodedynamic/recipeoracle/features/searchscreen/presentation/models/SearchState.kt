package com.kodedynamic.recipeoracle.features.searchscreen.presentation.models

import com.kodedynamic.recipeoracle.apis.domain.models.RecipeModel
import com.kodedynamic.recipeoracle.common.Empty
import com.kodedynamic.recipeoracle.common.ScreenEvent
import com.kodedynamic.recipeoracle.common.State

data class SearchState(
    override val id: String = String.Empty,
    val isLoading: Boolean = false,
    val screenEvent: ScreenEvent = ScreenEvent.None,
    val recipesList: List<RecipeModel> = emptyList()
) : State
