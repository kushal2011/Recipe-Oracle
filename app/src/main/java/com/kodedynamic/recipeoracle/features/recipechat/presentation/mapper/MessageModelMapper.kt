package com.kodedynamic.recipeoracle.features.recipechat.presentation.mapper

import com.kodedynamic.recipeoracle.apis.data.models.MessageDto
import com.kodedynamic.recipeoracle.common.Mapper
import com.kodedynamic.recipeoracle.features.recipechat.presentation.models.MessageModel
import javax.inject.Inject

private const val USER = "user"

class MessageModelMapper @Inject constructor() : Mapper<MessageDto, MessageModel> {
    override fun map(param: MessageDto): MessageModel {
        return MessageModel(
            message = param.content.orEmpty(),
            isMessageByUser = param.role == USER
        )
    }
}