package com.kdtech.recipeoracle.apis.data.models


import com.google.gson.annotations.SerializedName

data class RecipeModel(
    @SerializedName("image_url")
    val image: String,
    @SerializedName("cuisine_type")
    val cuisineType: String,
    @SerializedName("course")
    val course: String,
    @SerializedName("ingredients")
    val ingredients: List<Ingredient>,
    @SerializedName("instructions")
    val instructions: List<Instruction>,
    @SerializedName("is_eggiterian")
    val isEggiterian: Boolean,
    @SerializedName("is_jain")
    val isJain: Boolean,
    @SerializedName("is_non_veg")
    val isNonVeg: Boolean,
    @SerializedName("is_vegan")
    val isVegan: Boolean,
    @SerializedName("is_vegetarian")
    val isVegetarian: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("prep_time")
    val prepTime: Int,
    @SerializedName("health_rating")
    val healthRating: Int
)

data class Instruction(
    @SerializedName("step")
    val step: String
)
data class Ingredient(
    @SerializedName("name")
    val name: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("image_url")
    val image: String
)