package com.kodedynamic.recipeoracle.apis.domain.usecase

import com.kodedynamic.recipeoracle.apis.data.repositories.RecipesRepository
import com.kodedynamic.recipeoracle.apis.domain.UseCase
import com.kodedynamic.recipeoracle.apis.domain.models.CategoriesModel
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val recipeRepository: RecipesRepository
) : UseCase.SuspendingParameterized<Long, Result<CategoriesModel>> {
    override suspend fun invoke(
        param: Long
    ) : Result<CategoriesModel>{
        return recipeRepository.getCategories(param)
    }
}