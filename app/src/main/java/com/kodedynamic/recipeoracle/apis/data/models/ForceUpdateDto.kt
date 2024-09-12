package com.kodedynamic.recipeoracle.apis.data.models


import com.google.gson.annotations.SerializedName

data class ForceUpdateDto(
    @SerializedName("shouldForceUpdate")
    val shouldForceUpdate: Boolean?,
    @SerializedName("updateMessage")
    val updateMessage: String?
)