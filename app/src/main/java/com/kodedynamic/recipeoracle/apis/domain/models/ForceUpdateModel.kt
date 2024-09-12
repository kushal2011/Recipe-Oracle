package com.kodedynamic.recipeoracle.apis.domain.models


data class ForceUpdateModel(
    val shouldForceUpdate: Boolean,
    val updateMessage: String
)