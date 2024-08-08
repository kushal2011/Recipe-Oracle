package com.kdtech.recipeoracle.apis.data.networks

import com.kdtech.recipeoracle.apis.data.models.HomeFeedWidgetsDto
import com.kdtech.recipeoracle.apis.data.models.RecipeDto

interface RecipesDataSource {
    suspend fun getRecipes(prompt: String): Result<List<RecipeDto>>
    suspend fun getLocallyStoredRecipes(): Result<List<RecipeDto>>
    suspend fun getHomeFeedDataFromRemote(): Result<HomeFeedWidgetsDto>
    suspend fun getHomeFeedDataFromLocal(): Result<HomeFeedWidgetsDto>
}