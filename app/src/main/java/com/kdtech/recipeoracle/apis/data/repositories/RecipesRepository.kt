package com.kdtech.recipeoracle.apis.data.repositories

import com.kdtech.recipeoracle.apis.domain.models.CategoriesModel
import com.kdtech.recipeoracle.apis.domain.models.HomeFeedWidgetsModel
import com.kdtech.recipeoracle.apis.domain.models.RecipeModel
import com.kdtech.recipeoracle.apis.domain.models.RecipeRequestModel
import com.kdtech.recipeoracle.apis.domain.models.SeeAllRecipeRequest

interface RecipesRepository {
    suspend fun getRecipes(recipeRequest: RecipeRequestModel): Result<List<RecipeModel>>
    suspend fun getHomeFeedData(configVersion: Long): Result<HomeFeedWidgetsModel>
    suspend fun getCategories(): Result<CategoriesModel>
    suspend fun getSeeAllRecipes(seeAllRecipeRequest: SeeAllRecipeRequest): Result<List<RecipeModel>>
    suspend fun getSearchedRecipes(searchText: String): Result<List<RecipeModel>>
}