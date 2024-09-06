package com.kodedynamic.recipeoracle.features.homescreen.presentation.models

import com.kodedynamic.recipeoracle.apis.data.models.RecipeDto
import com.kodedynamic.recipeoracle.apis.domain.models.WidgetModel
import com.kodedynamic.recipeoracle.common.Empty
import com.kodedynamic.recipeoracle.common.ScreenEvent
import com.kodedynamic.recipeoracle.common.State

data class HomeState(
    override val id: String = String.Empty,
    var isLoading: Boolean = false,
    var recipeList: List<RecipeDto> = emptyList(),
    val homeFeedWidgets: List<WidgetModel> = emptyList(),
    val screenEvent: ScreenEvent = ScreenEvent.None
) : State