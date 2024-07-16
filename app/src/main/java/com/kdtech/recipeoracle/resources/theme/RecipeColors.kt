package com.kdtech.recipeoracle.resources.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class RecipeColors(
    white: Color,
    black: Color,
    primaryGreen: Color
) {
    var transparent by mutableStateOf(Color.Transparent)
        private set
    var white100 by mutableStateOf(white)
        private set
    var black100 by mutableStateOf(black)
        private set
    var primaryGreen by mutableStateOf(primaryGreen)
        private set

    fun copy(
        white: Color = this.white100,
        black: Color = this.black100,
        primaryGreen: Color = this.primaryGreen
    ): RecipeColors = RecipeColors(
        white = white,
        black = black,
        primaryGreen = primaryGreen
    )

}

fun colorsSet(
    white: Color = whiteColor,
    black: Color = blackColor,
    primaryGreen: Color = primaryGreenColor,
): RecipeColors = RecipeColors(
    white = white,
    black = black,
    primaryGreen = primaryGreen
)

val LocalColors = staticCompositionLocalOf { colorsSet() }


val whiteColor: Color = Color(color = 0xFFFFFFFF)
val blackColor: Color = Color(color = 0xFF000000)
val primaryGreenColor: Color = Color(color = 0xFFFFFFFF)