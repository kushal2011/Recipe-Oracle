package com.kodedynamic.recipeoracle.features.seeallscreen.presentation.models

import com.kodedynamic.recipeoracle.apis.domain.models.RecipeModel
import com.kodedynamic.recipeoracle.common.Empty
import com.kodedynamic.recipeoracle.common.ScreenEvent
import com.kodedynamic.recipeoracle.common.State

data class SeeAllState(
    override val id: String = String.Empty,
    val recipes: List<RecipeModel> = emptyList(),
    val screenTitle: String = String.Empty,
    val screenEvent: ScreenEvent = ScreenEvent.None,
    val isLoading: Boolean = false
): State