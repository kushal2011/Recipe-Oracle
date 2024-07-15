package com.kdtech.recipeoracle.apis.data.repositories

import com.kdtech.recipeoracle.apis.data.models.RecipeModel

interface RecipesRepository {
    suspend fun getRecipes(prompt: String): Result<List<RecipeModel>>
}