package com.kodedynamic.recipeoracle.apis.domain.models

data class ChoiceModel(
    val finishReason: String,
    val index: Int,
    val messageModel: MessageModel?
)