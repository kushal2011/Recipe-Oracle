package com.kodedynamic.recipeoracle.apis.data.models


import com.google.gson.annotations.SerializedName

data class HomeFeedWidgetsDto(
    @SerializedName("widgets")
    val widgetsList: List<WidgetDto>?
)