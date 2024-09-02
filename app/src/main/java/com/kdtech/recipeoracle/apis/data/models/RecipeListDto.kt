package com.kdtech.recipeoracle.apis.data.models

import com.google.gson.annotations.SerializedName

data class RecipeListDto(
    @SerializedName("data")
    val recipesList: List<RecipeDto>?
)
