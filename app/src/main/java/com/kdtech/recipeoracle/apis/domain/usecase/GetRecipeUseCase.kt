package com.kdtech.recipeoracle.apis.domain.usecase

import com.kdtech.recipeoracle.apis.data.models.RecipeModel
import com.kdtech.recipeoracle.apis.data.models.RecipeRequestModel
import com.kdtech.recipeoracle.apis.data.repositories.RecipesRepository
import com.kdtech.recipeoracle.apis.domain.UseCase
import com.kdtech.recipeoracle.coroutines.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipesRepository,
    private val dispatcherProvider: DispatcherProvider
) : UseCase.SuspendingParameterized<RecipeRequestModel, Result<List<RecipeModel>>> {
    override suspend fun invoke(
        param: RecipeRequestModel
    ): Result<List<RecipeModel>> = withContext(dispatcherProvider.io) {
        recipeRepository.getRecipes(param)
    }
}
