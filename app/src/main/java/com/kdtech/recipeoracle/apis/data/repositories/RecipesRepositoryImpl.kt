package com.kdtech.recipeoracle.apis.data.repositories

import com.kdtech.recipeoracle.apis.data.models.RecipeModel
import com.kdtech.recipeoracle.apis.data.models.RecipeRequestModel
import com.kdtech.recipeoracle.apis.data.networks.RecipesDataSource
import com.kdtech.recipeoracle.coroutines.DispatcherProvider
import com.kdtech.recipeoracle.prompt.Prompts
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val recipesDataSource: RecipesDataSource,
    private val dispatcherProvider: DispatcherProvider
): RecipesRepository {
    override suspend fun getRecipes(
        recipeRequest: RecipeRequestModel
    ): Result<List<RecipeModel>> = withContext(dispatcherProvider.io) {
        val prompt = Prompts.getPromptForRecipes(recipeRequest)
        recipesDataSource.getRecipes(prompt)
    }
}