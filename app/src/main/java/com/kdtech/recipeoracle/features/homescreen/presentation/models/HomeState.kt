package com.kdtech.recipeoracle.features.homescreen.presentation.models

import com.kdtech.recipeoracle.data.IngredientModel
import com.kdtech.recipeoracle.data.RecipeModel

data class HomeState(
    var isLoading: Boolean = false,
    var recipeList: List<RecipeModel> = emptyList(),
    var ingredientsList: List<IngredientModel> = emptyList()
)