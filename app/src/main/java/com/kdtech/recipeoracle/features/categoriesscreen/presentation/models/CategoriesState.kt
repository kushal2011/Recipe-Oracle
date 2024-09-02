package com.kdtech.recipeoracle.features.categoriesscreen.presentation.models

import com.kdtech.recipeoracle.common.Empty
import com.kdtech.recipeoracle.common.State

data class CategoriesState(
    override val id: String = String.Empty,
    val categories: List<CategoryModel> = emptyList()
): State
