package com.kdtech.recipeoracle.apis.data.models


import com.google.gson.annotations.SerializedName

data class WidgetDto(
    @SerializedName("data")
    val recipesList: List<RecipeDto>?,
    @SerializedName("id")
    val widgetId: String?,
    @SerializedName("shouldShowSeeAll")
    val shouldShowSeeAll: Boolean?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("widget_type")
    val widgetType: String?,
    @SerializedName("see_all")
    val seeAll: SeeAllDto?
)