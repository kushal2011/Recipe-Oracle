package com.kodedynamic.recipeoracle.apis.domain.usecase

import com.kodedynamic.recipeoracle.apis.data.models.OpenAiChatRequestDto
import com.kodedynamic.recipeoracle.apis.data.repositories.RecipesRepository
import com.kodedynamic.recipeoracle.apis.domain.UseCase
import com.kodedynamic.recipeoracle.apis.domain.models.OpenAiChatModel
import javax.inject.Inject

class ChatWithOpenAiUseCase @Inject constructor(
    private val recipeRepository: RecipesRepository
) : UseCase.SuspendingParameterized<OpenAiChatRequestDto, Result<OpenAiChatModel>> {
    override suspend fun invoke(
        param: OpenAiChatRequestDto
    ) : Result<OpenAiChatModel>{
        return recipeRepository.chatWithOpenAi(param)
    }
}