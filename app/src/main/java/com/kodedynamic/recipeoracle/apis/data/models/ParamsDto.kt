package com.kodedynamic.recipeoracle.apis.data.models


import com.google.gson.annotations.SerializedName

data class ParamsDto(
    @SerializedName("cuisine_type")
    val cuisineType: String?,
    @SerializedName("health_rating")
    val healthRating: Int?,
    @SerializedName("prep_time")
    val prepTime: Int?,
    @SerializedName("top_rated")
    val topRated: Boolean?
)