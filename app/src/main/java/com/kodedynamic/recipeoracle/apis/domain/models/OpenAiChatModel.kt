package com.kodedynamic.recipeoracle.apis.domain.models

data class OpenAiChatModel(
    val choices: List<ChoiceModel>,
    val created: Int,
    val id: String,
    val model: String
)