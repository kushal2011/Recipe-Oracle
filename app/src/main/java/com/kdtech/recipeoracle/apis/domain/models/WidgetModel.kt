package com.kdtech.recipeoracle.apis.domain.models

data class WidgetModel(
    val recipesList: List<RecipeModel>,
    val widgetId: String,
    val shouldShowSeeAll: Boolean,
    val title: String,
    val widgetType: String
)
