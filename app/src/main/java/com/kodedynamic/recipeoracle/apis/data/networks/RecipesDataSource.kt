package com.kodedynamic.recipeoracle.apis.data.networks

import com.kodedynamic.recipeoracle.apis.data.models.CategoriesDto
import com.kodedynamic.recipeoracle.apis.data.models.ForceUpdateDto
import com.kodedynamic.recipeoracle.apis.data.models.HomeFeedWidgetsDto
import com.kodedynamic.recipeoracle.apis.data.models.OpenAiChatDto
import com.kodedynamic.recipeoracle.apis.data.models.OpenAiChatRequestDto
import com.kodedynamic.recipeoracle.apis.data.models.RecipeDto
import com.kodedynamic.recipeoracle.apis.data.models.RecipeListDto
import com.kodedynamic.recipeoracle.apis.domain.models.SeeAllRecipeRequest

interface RecipesDataSource {
    suspend fun getRecipes(prompt: String): Result<RecipeListDto>
    suspend fun getRecipeById(recipeId: String): Result<RecipeDto>
    suspend fun getHomeFeedDataFromRemote(configVersion: Long): Result<HomeFeedWidgetsDto>
    suspend fun getHomeFeedDataFromLocal(configVersion: Long): Result<HomeFeedWidgetsDto>
    suspend fun getHomeFeedData(configVersion: Long): Result<HomeFeedWidgetsDto>
    suspend fun getCategoriesData(configVersion: Long): Result<CategoriesDto>
    suspend fun getCategoriesFromRemote(configVersion: Long): Result<CategoriesDto>
    suspend fun getCategoriesDataFromLocal(configVersion: Long): Result<CategoriesDto>
    suspend fun getSeeAllRecipes(seeAllRecipeRequest: SeeAllRecipeRequest): Result<RecipeListDto>
    suspend fun getSearchedRecipes(searchText: String): Result<RecipeListDto>
    suspend fun postGeneratedRecipes(json: String): Result<Unit>
    suspend fun chatWithOpenAi(chatRequestDto: OpenAiChatRequestDto): Result<OpenAiChatDto>
    suspend fun getIfForceUpdate(versionNo: Int): Result<ForceUpdateDto>
}