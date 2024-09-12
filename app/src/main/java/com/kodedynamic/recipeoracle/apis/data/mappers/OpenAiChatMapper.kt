package com.kodedynamic.recipeoracle.apis.data.mappers

import com.kodedynamic.recipeoracle.apis.data.models.OpenAiChatDto
import com.kodedynamic.recipeoracle.apis.domain.models.OpenAiChatModel
import com.kodedynamic.recipeoracle.common.Mapper
import javax.inject.Inject

class OpenAiChatMapper @Inject constructor(
    private val choiceMapper: ChoiceMapper
) : Mapper<OpenAiChatDto, OpenAiChatModel> {
    override fun map(param: OpenAiChatDto): OpenAiChatModel {
        return OpenAiChatModel(
            choices = param.choiceDtos?.map { choiceMapper.map(it) } ?: emptyList(),
            model = param.model.orEmpty(),
            id = param.id.orEmpty(),
            created = param.created ?: 0
        )
    }
}