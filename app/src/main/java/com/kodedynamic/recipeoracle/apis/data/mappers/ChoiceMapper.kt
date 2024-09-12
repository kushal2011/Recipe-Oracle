package com.kodedynamic.recipeoracle.apis.data.mappers

import com.kodedynamic.recipeoracle.apis.data.models.ChoiceDto
import com.kodedynamic.recipeoracle.apis.domain.models.ChoiceModel
import com.kodedynamic.recipeoracle.common.Mapper
import javax.inject.Inject
import kotlin.random.Random

class ChoiceMapper @Inject constructor(
    private val messageMapper: MessageMapper
) : Mapper<ChoiceDto, ChoiceModel> {
    override fun map(param: ChoiceDto): ChoiceModel {
        return ChoiceModel(
            messageModel = param.messageDto?.let { messageMapper.map(it) },
            index = param.index?:Random.nextInt(),
            finishReason = param.finishReason.orEmpty()
        )
    }
}