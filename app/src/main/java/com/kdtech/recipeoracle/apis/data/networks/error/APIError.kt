package com.kdtech.recipeoracle.apis.data.networks.error


import com.google.gson.annotations.SerializedName

data class APIError(
    @SerializedName("detail")
    val detail: List<Detail?>?
)