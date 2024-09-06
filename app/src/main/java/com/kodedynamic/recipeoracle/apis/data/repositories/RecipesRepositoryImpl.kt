package com.kodedynamic.recipeoracle.apis.data.repositories

import com.kodedynamic.recipeoracle.apis.data.mappers.CategoriesMapper
import com.kodedynamic.recipeoracle.apis.data.mappers.HomeFeedWidgetsMapper
import com.kodedynamic.recipeoracle.apis.data.mappers.RecipeListMapper
import com.kodedynamic.recipeoracle.apis.data.mappers.RecipeMapper
import com.kodedynamic.recipeoracle.apis.domain.models.RecipeRequestModel
import com.kodedynamic.recipeoracle.apis.data.networks.RecipesDataSource
import com.kodedynamic.recipeoracle.apis.domain.models.CategoriesModel
import com.kodedynamic.recipeoracle.apis.domain.models.HomeFeedWidgetsModel
import com.kodedynamic.recipeoracle.apis.domain.models.RecipeModel
import com.kodedynamic.recipeoracle.apis.domain.models.SeeAllRecipeRequest
import com.kodedynamic.recipeoracle.coroutines.DispatcherProvider
import com.kodedynamic.recipeoracle.prompt.Prompts
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val recipesDataSource: RecipesDataSource,
    private val dispatcherProvider: DispatcherProvider,
    private val homeFeedWidgetsMapper: HomeFeedWidgetsMapper,
    private val categoriesMapper: CategoriesMapper,
    private val recipeListMapper: RecipeListMapper,
    private val recipeMapper: RecipeMapper
): RecipesRepository {
    override suspend fun getRecipes(
        recipeRequest: RecipeRequestModel
    ): Result<List<RecipeModel>> = withContext(dispatcherProvider.io) {
        val prompt = Prompts.getPromptForRecipes(recipeRequest)
        recipesDataSource.getRecipes(prompt).map { recipeListMapper.map(it) }
    }

    override suspend fun getRecipeById(recipeId: String): Result<RecipeModel> {
        return withContext(dispatcherProvider.io) {
            recipesDataSource.getRecipeById(recipeId).map { recipeMapper.map(it) }
        }
    }

    override suspend fun getHomeFeedData(configVersion: Long): Result<HomeFeedWidgetsModel> {
        return withContext(dispatcherProvider.io) {
            recipesDataSource.getHomeFeedData(configVersion).map { homeFeedWidgetsMapper.map(it) }
        }
    }

    override suspend fun getCategories(configVersion: Long): Result<CategoriesModel> {
        return withContext(dispatcherProvider.io) {
            recipesDataSource.getCategoriesData(configVersion).map { categoriesMapper.map(it) }
        }
    }

    override suspend fun getSeeAllRecipes(
        seeAllRecipeRequest: SeeAllRecipeRequest
    ): Result<List<RecipeModel>> {
        return withContext(dispatcherProvider.io) {
            recipesDataSource.getSeeAllRecipes(seeAllRecipeRequest).map { recipeListMapper.map(it) }
        }
    }

    override suspend fun getSearchedRecipes(searchText: String): Result<List<RecipeModel>> {
        return withContext(dispatcherProvider.io) {
            recipesDataSource.getSearchedRecipes(searchText).map { recipeListMapper.map(it) }
        }
    }

    override suspend fun postGeneratedRecipes(json: String): Result<Unit> {
        return withContext(dispatcherProvider.io) {
            recipesDataSource.postGeneratedRecipes(json)
        }
    }
}