package com.kdtech.recipeoracle.apis.data.models


import com.google.gson.annotations.SerializedName

data class CategoriesDto(
    @SerializedName("cuisines")
    val cuisines: List<CuisineDto>?
)