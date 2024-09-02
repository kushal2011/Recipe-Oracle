package com.kdtech.recipeoracle.apis.domain.usecase

import com.kdtech.recipeoracle.apis.data.repositories.RecipesRepository
import com.kdtech.recipeoracle.apis.domain.UseCase
import com.kdtech.recipeoracle.apis.domain.models.CategoriesModel
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val recipeRepository: RecipesRepository
) : UseCase.Suspending<Result<CategoriesModel>> {
    override suspend fun invoke() : Result<CategoriesModel>{
        return recipeRepository.getCategories()
    }
}