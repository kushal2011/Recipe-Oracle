package com.kodedynamic.recipeoracle.apis.domain.models

import com.kodedynamic.recipeoracle.apis.data.models.SeeAllDto

data class WidgetModel(
    val recipesList: List<RecipeModel>,
    val widgetId: String,
    val shouldShowSeeAll: Boolean,
    val title: String,
    val widgetType: String,
    val seeAll: SeeAllDto?
)
