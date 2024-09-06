package com.kodedynamic.recipeoracle.apis.data.repositories

import com.kodedynamic.recipeoracle.apis.domain.models.CategoriesModel
import com.kodedynamic.recipeoracle.apis.domain.models.HomeFeedWidgetsModel
import com.kodedynamic.recipeoracle.apis.domain.models.RecipeModel
import com.kodedynamic.recipeoracle.apis.domain.models.RecipeRequestModel
import com.kodedynamic.recipeoracle.apis.domain.models.SeeAllRecipeRequest

interface RecipesRepository {
    suspend fun getRecipes(recipeRequest: RecipeRequestModel): Result<List<RecipeModel>>
    suspend fun getHomeFeedData(configVersion: Long): Result<HomeFeedWidgetsModel>
    suspend fun getCategories(configVersion: Long): Result<CategoriesModel>
    suspend fun getSeeAllRecipes(seeAllRecipeRequest: SeeAllRecipeRequest): Result<List<RecipeModel>>
    suspend fun getSearchedRecipes(searchText: String): Result<List<RecipeModel>>
    suspend fun postGeneratedRecipes(json: String): Result<Unit>
}