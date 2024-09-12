package com.kodedynamic.recipeoracle.apis.data.models


import com.google.gson.annotations.SerializedName

data class OpenAiChatRequestDto(
    @SerializedName("messages")
    val messages: List<MessageDto>,
    @SerializedName("model")
    val model: String
)