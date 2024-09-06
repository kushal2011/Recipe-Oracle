package com.kodedynamic.recipeoracle.apis.data.models


import com.google.gson.annotations.SerializedName

data class InstructionDto(
    @SerializedName("id")
    val instructionId: String?= null,
    @SerializedName("recipe_id")
    val recipeId: String?= null,
    @SerializedName("step")
    val step: String?
)