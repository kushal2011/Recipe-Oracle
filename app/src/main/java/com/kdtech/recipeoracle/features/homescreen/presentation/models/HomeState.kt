package com.kdtech.recipeoracle.features.homescreen.presentation.models

import com.kdtech.recipeoracle.apis.data.models.RecipeDto
import com.kdtech.recipeoracle.apis.domain.models.WidgetModel
import com.kdtech.recipeoracle.common.Empty
import com.kdtech.recipeoracle.common.ScreenEvent
import com.kdtech.recipeoracle.common.State

data class HomeState(
    override val id: String = String.Empty,
    var isLoading: Boolean = false,
    var recipeList: List<RecipeDto> = emptyList(),
    val homeFeedWidgets: List<WidgetModel> = emptyList(),
    val screenEvent: ScreenEvent = ScreenEvent.None
) : State