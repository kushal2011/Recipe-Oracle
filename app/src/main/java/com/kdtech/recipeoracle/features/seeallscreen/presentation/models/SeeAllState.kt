package com.kdtech.recipeoracle.features.seeallscreen.presentation.models

import com.kdtech.recipeoracle.apis.domain.models.RecipeModel
import com.kdtech.recipeoracle.common.Empty
import com.kdtech.recipeoracle.common.ScreenEvent
import com.kdtech.recipeoracle.common.State

data class SeeAllState(
    override val id: String = String.Empty,
    val recipes: List<RecipeModel> = emptyList(),
    val screenTitle: String = String.Empty,
    val screenEvent: ScreenEvent = ScreenEvent.None,
    val isLoading: Boolean = false
): State