package com.kodedynamic.recipeoracle.apis.domain.usecase

import com.kodedynamic.recipeoracle.apis.data.repositories.RecipesRepository
import com.kodedynamic.recipeoracle.apis.domain.UseCase
import com.kodedynamic.recipeoracle.apis.domain.models.RecipeModel
import com.kodedynamic.recipeoracle.apis.domain.models.SeeAllRecipeRequest
import com.kodedynamic.recipeoracle.coroutines.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSeeAllRecipesUseCase @Inject constructor(
    private val recipeRepository: RecipesRepository,
    private val dispatcherProvider: DispatcherProvider
) : UseCase.SuspendingParameterized<SeeAllRecipeRequest, Result<List<RecipeModel>>> {
    override suspend fun invoke(
        param: SeeAllRecipeRequest
    ): Result<List<RecipeModel>> = withContext(dispatcherProvider.io) {
        recipeRepository.getSeeAllRecipes(param)
    }
}