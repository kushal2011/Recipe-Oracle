package com.kodedynamic.recipeoracle.apis.domain.usecase

import com.kodedynamic.recipeoracle.apis.data.repositories.RecipesRepository
import com.kodedynamic.recipeoracle.apis.domain.UseCase
import com.kodedynamic.recipeoracle.apis.domain.models.HomeFeedWidgetsModel
import javax.inject.Inject

class GetHomeFeedUseCase @Inject constructor(
    private val recipeRepository: RecipesRepository
) : UseCase.SuspendingParameterized<Long, Result<HomeFeedWidgetsModel>> {
    override suspend fun invoke(
        param: Long
    ) = recipeRepository.getHomeFeedData(param)
}