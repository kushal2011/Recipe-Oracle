package com.kodedynamic.recipeoracle.apis.data.models


import com.google.gson.annotations.SerializedName

data class ForceUpdateRequestDto(
    @SerializedName("versionNo")
    val versionNo: Int
)