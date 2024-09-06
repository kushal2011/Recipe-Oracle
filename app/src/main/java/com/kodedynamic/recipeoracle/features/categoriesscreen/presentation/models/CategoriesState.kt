package com.kodedynamic.recipeoracle.features.categoriesscreen.presentation.models

import com.kodedynamic.recipeoracle.apis.domain.models.CuisineModel
import com.kodedynamic.recipeoracle.common.Empty
import com.kodedynamic.recipeoracle.common.ScreenEvent
import com.kodedynamic.recipeoracle.common.State

data class CategoriesState(
    override val id: String = String.Empty,
    val cuisines: List<CuisineModel> = emptyList(),
    val screenEvent: ScreenEvent = ScreenEvent.None,
    val isLoading: Boolean = false
): State
