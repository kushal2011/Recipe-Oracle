package com.kdtech.recipeoracle.features.homescreen.presentation.models

import com.kdtech.recipeoracle.apis.data.models.RecipeDto

data class HomeState(
    var isLoading: Boolean = false,
    var recipeList: List<RecipeDto> = emptyList(),
//    var ingredientsList: List<IngredientModel> = emptyList()
)