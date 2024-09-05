package com.kdtech.recipeoracle.features.searchscreen.presentation.models

import com.kdtech.recipeoracle.apis.domain.models.RecipeModel
import com.kdtech.recipeoracle.common.Empty
import com.kdtech.recipeoracle.common.ScreenEvent
import com.kdtech.recipeoracle.common.State

data class SearchState(
    override val id: String = String.Empty,
    val isLoading: Boolean = false,
    val screenEvent: ScreenEvent = ScreenEvent.None,
    val recipesList: List<RecipeModel> = emptyList()
) : State
