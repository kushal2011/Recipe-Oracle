package com.kdtech.recipeoracle.features.homescreen.presentation.models

data class HomeState(
    var isLoading: Boolean = false,
    var recipeText: String = ""
)