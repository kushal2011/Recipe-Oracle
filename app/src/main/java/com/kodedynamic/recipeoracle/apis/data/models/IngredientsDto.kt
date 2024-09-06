package com.kodedynamic.recipeoracle.apis.data.models


import com.google.gson.annotations.SerializedName

data class IngredientsDto(
    @SerializedName("id")
    val ingredientId: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("name")
    val ingredientName: String?,
    @SerializedName("quantity")
    val quantity: String?,
    @SerializedName("recipe_id")
    val recipeId: String?
)