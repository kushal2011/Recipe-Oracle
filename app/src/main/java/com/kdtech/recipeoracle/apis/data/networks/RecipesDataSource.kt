package com.kdtech.recipeoracle.apis.data.networks

import com.kdtech.recipeoracle.apis.data.models.CategoriesDto
import com.kdtech.recipeoracle.apis.data.models.HomeFeedWidgetsDto
import com.kdtech.recipeoracle.apis.data.models.RecipeDto
import com.kdtech.recipeoracle.apis.data.models.RecipeListDto
import com.kdtech.recipeoracle.apis.domain.models.SeeAllRecipeRequest

interface RecipesDataSource {
    suspend fun getRecipes(prompt: String): Result<List<RecipeDto>>
    suspend fun getLocallyStoredRecipes(): Result<List<RecipeDto>>
    suspend fun getHomeFeedDataFromRemote(configVersion: Long): Result<HomeFeedWidgetsDto>
    suspend fun getHomeFeedDataFromLocal(configVersion: Long): Result<HomeFeedWidgetsDto>
    suspend fun getHomeFeedData(configVersion: Long): Result<HomeFeedWidgetsDto>
    suspend fun getCategories(): Result<CategoriesDto>
    suspend fun getSeeAllRecipes(seeAllRecipeRequest: SeeAllRecipeRequest): Result<RecipeListDto>
    suspend fun getSearchedRecipes(searchText: String): Result<RecipeListDto>
}