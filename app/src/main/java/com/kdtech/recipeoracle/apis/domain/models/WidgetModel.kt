package com.kdtech.recipeoracle.apis.domain.models

import com.kdtech.recipeoracle.apis.data.models.SeeAllDto

data class WidgetModel(
    val recipesList: List<RecipeModel>,
    val widgetId: String,
    val shouldShowSeeAll: Boolean,
    val title: String,
    val widgetType: String,
    val seeAll: SeeAllDto?
)
