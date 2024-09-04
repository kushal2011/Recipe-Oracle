package com.kdtech.recipeoracle.apis.data.models


import com.google.gson.annotations.SerializedName

data class SeeAllDto(
    @SerializedName("endpoint")
    val endpoint: String?,
    @SerializedName("params")
    val params: ParamsDto?
)