package com.kodedynamic.recipeoracle.apis.data.mappers

import com.kodedynamic.recipeoracle.apis.data.models.MessageDto
import com.kodedynamic.recipeoracle.apis.domain.models.MessageModel
import com.kodedynamic.recipeoracle.common.Mapper
import javax.inject.Inject

class MessageMapper @Inject constructor() : Mapper<MessageDto, MessageModel> {
    override fun map(param: MessageDto): MessageModel {
        return MessageModel(
            content = param.content.orEmpty(),
            role = param.role.orEmpty()
        )
    }
}