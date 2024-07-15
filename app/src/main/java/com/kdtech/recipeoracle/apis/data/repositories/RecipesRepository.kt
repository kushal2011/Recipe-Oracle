package com.kdtech.recipeoracle.apis.data.repositories

import com.kdtech.recipeoracle.apis.data.models.RecipeModel
import com.kdtech.recipeoracle.apis.data.models.RecipeRequestModel

interface RecipesRepository {
    suspend fun getRecipes(recipeRequest: RecipeRequestModel): Result<List<RecipeModel>>
}