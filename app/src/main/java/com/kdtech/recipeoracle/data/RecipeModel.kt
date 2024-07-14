package com.kdtech.recipeoracle.data


import com.google.gson.annotations.SerializedName

data class RecipeModel(
    @SerializedName("image")
    val image: String,
    @SerializedName("ingredients")
    val ingredients: List<Ingredient>,
    @SerializedName("instructions")
    val instructions: List<String>,
    @SerializedName("isEggiterian")
    val isEggiterian: Boolean,
    @SerializedName("isJain")
    val isJain: Boolean,
    @SerializedName("isNonVeg")
    val isNonVeg: Boolean,
    @SerializedName("isVegan")
    val isVegan: Boolean,
    @SerializedName("isVegetarian")
    val isVegetarian: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("prepTime")
    val prepTime: String
)


data class Ingredient(
    @SerializedName("name")
    val name: String,
    @SerializedName("quantity")
    val quantity: String
)