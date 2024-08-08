package com.kdtech.recipeoracle.apis.data.repositories

import com.kdtech.recipeoracle.apis.data.mappers.HomeFeedWidgetsMapper
import com.kdtech.recipeoracle.apis.data.models.RecipeDto
import com.kdtech.recipeoracle.apis.domain.models.RecipeRequestModel
import com.kdtech.recipeoracle.apis.data.networks.RecipesDataSource
import com.kdtech.recipeoracle.apis.domain.models.HomeFeedWidgetsModel
import com.kdtech.recipeoracle.coroutines.DispatcherProvider
import com.kdtech.recipeoracle.prompt.Prompts
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val recipesDataSource: RecipesDataSource,
    private val dispatcherProvider: DispatcherProvider,
    private val homeFeedWidgetsMapper: HomeFeedWidgetsMapper
): RecipesRepository {
    override suspend fun getRecipes(
        recipeRequest: RecipeRequestModel
    ): Result<List<RecipeDto>> = withContext(dispatcherProvider.io) {
        if (recipeRequest.areAllBooleansNull() && recipeRequest.searchText.isBlank()) {
            recipesDataSource.getLocallyStoredRecipes()
        } else {
            val prompt = Prompts.getPromptForRecipes(recipeRequest)
            recipesDataSource.getRecipes(prompt)
        }
    }

    override suspend fun getHomeFeedDataFromRemote(): Result<HomeFeedWidgetsModel> {
        return withContext(dispatcherProvider.io) {
            recipesDataSource.getHomeFeedDataFromRemote().map { homeFeedWidgetsMapper.map(it) }
        }
    }

    override suspend fun getHomeFeedDataFromLocal(): Result<HomeFeedWidgetsModel> {
        return withContext(dispatcherProvider.io) {
            recipesDataSource.getHomeFeedDataFromLocal().map { homeFeedWidgetsMapper.map(it) }
        }
    }
}