package com.kdtech.recipeoracle.data


import com.google.gson.annotations.SerializedName

data class IngredientModel(
    @SerializedName("calories")
    val calories: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("name")
    val name: String
)