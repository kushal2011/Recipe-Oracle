package com.kodedynamic.recipeoracle.apis.domain.models

data class IngredientsModel(
    val ingredientId: String,
    val imageUrl: String,
    val ingredientName: String,
    val quantity: String,
    val recipeId: String
)
