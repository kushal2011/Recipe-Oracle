package com.kodedynamic.recipeoracle.apis.data.models

import com.google.gson.annotations.SerializedName

data class OpenAiChatDto(
    @SerializedName("choices")
    val choiceDtos: List<ChoiceDto>?,
    @SerializedName("created")
    val created: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("model")
    val model: String?,
    @SerializedName("object")
    val objectX: String?,
    @SerializedName("system_fingerprint")
    val systemFingerprint: String?
)