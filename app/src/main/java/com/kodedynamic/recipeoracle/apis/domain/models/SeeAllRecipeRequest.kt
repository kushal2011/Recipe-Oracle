package com.kodedynamic.recipeoracle.apis.domain.models

data class SeeAllRecipeRequest(
    val cuisineType: String?= null,
    val prepTime: Int?= null,
    val healthRating: Int?= null,
    val topRated: Boolean?= null
)
