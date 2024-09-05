package com.kdtech.recipeoracle.features.categoriesscreen.presentation.models

import com.kdtech.recipeoracle.apis.domain.models.CuisineModel
import com.kdtech.recipeoracle.common.Empty
import com.kdtech.recipeoracle.common.ScreenEvent
import com.kdtech.recipeoracle.common.State

data class CategoriesState(
    override val id: String = String.Empty,
    val cuisines: List<CuisineModel> = emptyList(),
    val screenEvent: ScreenEvent = ScreenEvent.None,
    val isLoading: Boolean = false
): State
