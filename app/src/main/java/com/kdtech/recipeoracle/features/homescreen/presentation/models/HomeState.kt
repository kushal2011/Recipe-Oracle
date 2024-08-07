package com.kdtech.recipeoracle.features.homescreen.presentation.models

import com.kdtech.recipeoracle.apis.data.models.RecipeDto
import com.kdtech.recipeoracle.apis.domain.models.HomeFeedWidgetsModel
import com.kdtech.recipeoracle.apis.domain.models.WidgetModel

data class HomeState(
    var isLoading: Boolean = false,
    var recipeList: List<RecipeDto> = emptyList(),
    val homeFeedWidgets: List<WidgetModel> = emptyList()
//    var ingredientsList: List<IngredientModel> = emptyList()
)