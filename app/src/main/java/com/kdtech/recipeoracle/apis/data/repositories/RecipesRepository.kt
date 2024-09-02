package com.kdtech.recipeoracle.apis.data.repositories

import com.kdtech.recipeoracle.apis.data.models.RecipeDto
import com.kdtech.recipeoracle.apis.domain.models.CategoriesModel
import com.kdtech.recipeoracle.apis.domain.models.HomeFeedWidgetsModel
import com.kdtech.recipeoracle.apis.domain.models.RecipeRequestModel

interface RecipesRepository {
    suspend fun getRecipes(recipeRequest: RecipeRequestModel): Result<List<RecipeDto>>
    suspend fun getHomeFeedData(configVersion: Long): Result<HomeFeedWidgetsModel>
    suspend fun getCategories(): Result<CategoriesModel>
}