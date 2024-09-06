package com.kodedynamic.recipeoracle.apis.domain.models

data class RecipeModel(
    val averageRating: Double,
    val course: String,
    val cuisineType: String,
    val healthRating: Int,
    val recipeId: String,
    val imageUrl: String,
    val ingredients: List<IngredientsModel>,
    val instructions: List<InstructionModel>,
    val isEggiterian: Boolean,
    val isJain: Boolean,
    val isNonVeg: Boolean,
    val isVegan: Boolean,
    val isVegetarian: Boolean,
    val recipeName: String,
    val prepTime: Int,
    val ratingsCount: Int
)
