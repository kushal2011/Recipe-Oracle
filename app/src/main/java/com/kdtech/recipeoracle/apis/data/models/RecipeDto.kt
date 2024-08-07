package com.kdtech.recipeoracle.apis.data.models


import com.google.gson.annotations.SerializedName

data class RecipeDto(
    @SerializedName("average_rating")
    val averageRating: Double?,
    @SerializedName("course")
    val course: String?,
    @SerializedName("cuisine_type")
    val cuisineType: String?,
    @SerializedName("health_rating")
    val healthRating: Int?,
    @SerializedName("id")
    val recipeId: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("ingredients")
    val ingredientsDtos: List<IngredientsDto>?,
    @SerializedName("instructions")
    val instructionDtos: List<InstructionDto>?,
    @SerializedName("is_eggiterian")
    val isEggiterian: Boolean?,
    @SerializedName("is_jain")
    val isJain: Boolean?,
    @SerializedName("is_non_veg")
    val isNonVeg: Boolean?,
    @SerializedName("is_vegan")
    val isVegan: Boolean?,
    @SerializedName("is_vegetarian")
    val isVegetarian: Boolean?,
    @SerializedName("name")
    val recipeName: String?,
    @SerializedName("prep_time")
    val prepTime: Int?,
    @SerializedName("ratings_count")
    val ratingsCount: Int?
)