package com.kdtech.recipeoracle.apis.data.networks

import com.kdtech.recipeoracle.apis.data.models.RecipeModel

interface RecipesDataSource {
    suspend fun getRecipes(prompt: String): Result<List<RecipeModel>>
}