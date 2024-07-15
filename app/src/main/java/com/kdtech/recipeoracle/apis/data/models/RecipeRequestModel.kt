package com.kdtech.recipeoracle.apis.data.models

import com.kdtech.recipeoracle.common.Empty

data class RecipeRequestModel(
    val searchText: String = String.Empty,
    val isVegetarian: Boolean? = null,
    val isNonVegetarian: Boolean? = null,
    val isEggiterian: Boolean? = null,
    val isVegan: Boolean? = null,
    val isJain: Boolean? = null
) {
    fun areAllBooleansNull(): Boolean {
        return listOf(isVegetarian, isNonVegetarian, isEggiterian, isVegan, isJain).all { it == null }
    }
}
