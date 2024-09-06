package com.kodedynamic.recipeoracle.apis.data.models


import com.google.gson.annotations.SerializedName

data class InstructionDto(
    @SerializedName("id")
    val instructionId: String?,
    @SerializedName("recipe_id")
    val recipeId: String?,
    @SerializedName("step")
    val step: String?
)