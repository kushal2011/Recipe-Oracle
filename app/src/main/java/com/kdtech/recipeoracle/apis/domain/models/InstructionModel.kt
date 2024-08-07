package com.kdtech.recipeoracle.apis.domain.models

data class InstructionModel(
    val instructionId: String,
    val recipeId: String,
    val step: String
)
