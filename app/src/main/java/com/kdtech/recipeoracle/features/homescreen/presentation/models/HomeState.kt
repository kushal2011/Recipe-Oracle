package com.kdtech.recipeoracle.features.homescreen.presentation.models

import com.kdtech.recipeoracle.apis.data.models.IngredientModel
import com.kdtech.recipeoracle.apis.data.models.RecipeModel

data class HomeState(
    var isLoading: Boolean = false,
    var recipeList: List<RecipeModel> = emptyList(),
    var ingredientsList: List<IngredientModel> = emptyList()
)