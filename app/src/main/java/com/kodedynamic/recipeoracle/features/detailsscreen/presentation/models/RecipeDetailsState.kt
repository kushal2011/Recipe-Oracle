package com.kodedynamic.recipeoracle.features.detailsscreen.presentation.models

import com.kodedynamic.recipeoracle.apis.domain.models.RecipeModel
import com.kodedynamic.recipeoracle.common.Empty
import com.kodedynamic.recipeoracle.common.ScreenEvent
import com.kodedynamic.recipeoracle.common.State

data class RecipeDetailsState(
    override val id: String = String.Empty,
    var recipeData: RecipeModel? = null,
    var isLoading: Boolean = false,
    val screenEvent: ScreenEvent = ScreenEvent.None
) : State