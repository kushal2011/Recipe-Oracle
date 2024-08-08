package com.kdtech.recipeoracle.apis.domain.usecase

import com.kdtech.recipeoracle.apis.data.repositories.RecipesRepository
import com.kdtech.recipeoracle.apis.domain.UseCase
import com.kdtech.recipeoracle.apis.domain.models.HomeFeedWidgetsModel
import com.kdtech.recipeoracle.common.Constants
import com.kdtech.recipeoracle.coroutines.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetHomeFeedUseCase @Inject constructor(
    private val recipeRepository: RecipesRepository,
    private val dispatcherProvider: DispatcherProvider
) : UseCase.SuspendingParameterized<Int, Result<HomeFeedWidgetsModel>> {
    override suspend fun invoke(
        param: Int
    ) = withContext(dispatcherProvider.io) {
        if (param > Constants.HOME_FEED_VERSION) {
            recipeRepository.getHomeFeedDataFromRemote()
        } else {
            recipeRepository.getHomeFeedDataFromLocal()
        }
    }
}