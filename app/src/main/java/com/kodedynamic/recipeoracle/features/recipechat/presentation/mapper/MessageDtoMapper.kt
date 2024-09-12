package com.kodedynamic.recipeoracle.features.recipechat.presentation.mapper

import com.kodedynamic.recipeoracle.apis.data.models.MessageDto
import com.kodedynamic.recipeoracle.common.Mapper
import com.kodedynamic.recipeoracle.features.recipechat.presentation.models.MessageModel
import javax.inject.Inject

private const val USER = "user"
private const val ASSISTANT = "assistant"

class MessageDtoMapper @Inject constructor() : Mapper<MessageModel, MessageDto> {
    override fun map(param: MessageModel): MessageDto {
        return MessageDto(
            role = if (param.isMessageByUser) USER else ASSISTANT,
            content = param.message
        )
    }
}