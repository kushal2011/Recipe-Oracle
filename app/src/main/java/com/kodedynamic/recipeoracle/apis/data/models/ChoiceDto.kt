package com.kodedynamic.recipeoracle.apis.data.models

import com.google.gson.annotations.SerializedName

data class ChoiceDto(
    @SerializedName("finish_reason")
    val finishReason: String?,
    @SerializedName("index")
    val index: Int?,
    @SerializedName("message")
    val messageDto: MessageDto?
)