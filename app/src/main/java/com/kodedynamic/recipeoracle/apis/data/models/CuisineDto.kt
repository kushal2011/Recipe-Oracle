package com.kodedynamic.recipeoracle.apis.data.models


import com.google.gson.annotations.SerializedName

data class CuisineDto(
    @SerializedName("cuisine_type")
    val cuisineType: String?,
    @SerializedName("image_url")
    val imageUrl: String?
)