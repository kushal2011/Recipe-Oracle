package com.kodedynamic.recipeoracle.apis.domain.usecase

import com.kodedynamic.recipeoracle.apis.data.repositories.RecipesRepository
import com.kodedynamic.recipeoracle.apis.domain.UseCase
import com.kodedynamic.recipeoracle.apis.domain.models.ForceUpdateModel
import javax.inject.Inject

class ForceUpdateUseCase @Inject constructor(
    private val recipeRepository: RecipesRepository
) : UseCase.SuspendingParameterized<Int, Result<ForceUpdateModel>> {
    override suspend fun invoke(
        param: Int
    ) : Result<ForceUpdateModel>{
        return recipeRepository.getIfForceUpdate(param)
    }
}