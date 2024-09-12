package com.kodedynamic.recipeoracle.apis.data.models

import com.google.gson.annotations.SerializedName

data class MessageDto(
    @SerializedName("content")
    val content: String?,
    @SerializedName("role")
    val role: String?
)