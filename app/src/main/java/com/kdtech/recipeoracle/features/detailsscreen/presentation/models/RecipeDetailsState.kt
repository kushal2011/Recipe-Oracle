package com.kdtech.recipeoracle.features.detailsscreen.presentation.models

import com.kdtech.recipeoracle.apis.domain.models.RecipeModel
import com.kdtech.recipeoracle.common.Empty
import com.kdtech.recipeoracle.common.State

data class RecipeDetailsState(
    override val id: String = String.Empty,
    var isLoading: Boolean = false,
    var recipeData: RecipeModel? = null
) : State