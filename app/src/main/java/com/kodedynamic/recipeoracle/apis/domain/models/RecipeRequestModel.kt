package com.kodedynamic.recipeoracle.apis.domain.models

import com.kodedynamic.recipeoracle.common.Empty

data class RecipeRequestModel(
    val searchText: String = String.Empty,
    val isVegetarian: Boolean? = null,
    val isNonVegetarian: Boolean? = null,
    val isEggiterian: Boolean? = null,
    val isVegan: Boolean? = null,
    val isJain: Boolean? = null,
    val noOfItemsToShow: Int = 5
) {
    fun areAllBooleansNull(): Boolean {
        return listOf(isVegetarian, isNonVegetarian, isEggiterian, isVegan, isJain).all { it == null }
    }
}
