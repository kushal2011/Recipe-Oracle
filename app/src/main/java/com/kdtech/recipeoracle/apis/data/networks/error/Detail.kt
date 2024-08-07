package com.kdtech.recipeoracle.apis.data.networks.error


import com.google.gson.annotations.SerializedName

data class Detail(
    @SerializedName("input")
    val input: String?,
    @SerializedName("loc")
    val loc: List<String?>?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("type")
    val type: String?
)