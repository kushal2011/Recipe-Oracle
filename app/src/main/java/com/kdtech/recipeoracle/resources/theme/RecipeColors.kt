package com.kdtech.recipeoracle.resources.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class RecipeColors(
    white: Color,
    black: Color,
    primaryGreen: Color,
    lightGreen: Color,
    lightGrey: Color,
    secondaryPink: Color,
    lightCyanBlue: Color,
    lightLimeGreen: Color,
    softPeach: Color,
    softCoral: Color,
    darkCharcoal: Color,
    mediumGray: Color,
    softMintBackground: Color,
    strongMintOverlay: Color,
    veryLightGray: Color,
    darkBackground: Color,
    errorColor: Color,
    warningColor: Color,
    lightDivider: Color,
    darkDivider: Color
) {
    var transparent by mutableStateOf(Color.Transparent)
        private set
    var white100 by mutableStateOf(white)
        private set
    var black100 by mutableStateOf(black)
        private set
    var primaryGreen by mutableStateOf(primaryGreen)
        private set
    var lightGreen by mutableStateOf(lightGreen)
        private set
    var lightGrey by mutableStateOf(lightGrey)
        private set
    var secondaryPink by mutableStateOf(secondaryPink)
        private set
    var lightCyanBlue by mutableStateOf(lightCyanBlue)
        private set
    var lightLimeGreen by mutableStateOf(lightLimeGreen)
        private set
    var softPeach by mutableStateOf(softPeach)
        private set
    var softCoral by mutableStateOf(softCoral)
        private set
    var darkCharcoal by mutableStateOf(darkCharcoal)
        private set
    var mediumGray by mutableStateOf(mediumGray)
        private set
    var softMintBackground by mutableStateOf(softMintBackground)
        private set
    var strongMintOverlay by mutableStateOf(strongMintOverlay)
        private set
    var veryLightGray by mutableStateOf(veryLightGray)
        private set
    var darkBackground by mutableStateOf(darkBackground)
        private set
    var errorColor by mutableStateOf(errorColor)
        private set
    var warningColor by mutableStateOf(warningColor)
        private set
    var lightDivider by mutableStateOf(lightDivider)
        private set
    var darkDivider by mutableStateOf(darkDivider)
        private set

    fun copy(
        white: Color = this.white100,
        black: Color = this.black100,
        primaryGreen: Color = this.primaryGreen,
        lightGreen: Color = this.lightGreen,
        lightGrey: Color = this.lightGrey,
        secondaryPink: Color = this.secondaryPink,
        lightCyanBlue: Color = this.lightCyanBlue,
        lightLimeGreen: Color = this.lightLimeGreen,
        softPeach: Color = this.softPeach,
        softCoral: Color = this.softCoral,
        darkCharcoal: Color = this.darkCharcoal,
        mediumGray: Color = this.mediumGray,
        softMintBackground: Color = this.softMintBackground,
        strongMintOverlay: Color = this.strongMintOverlay,
        veryLightGray: Color = this.veryLightGray,
        darkBackground: Color = this.darkBackground,
        errorColor: Color = this.errorColor,
        warningColor: Color = this.warningColor,
        lightDivider: Color = this.lightDivider,
        darkDivider: Color = this.darkDivider
    ): RecipeColors = RecipeColors(
        white = white,
        black = black,
        primaryGreen = primaryGreen,
        lightGreen = lightGreen,
        lightGrey = lightGrey,
        secondaryPink = secondaryPink,
        lightCyanBlue = lightCyanBlue,
        lightLimeGreen = lightLimeGreen,
        softPeach = softPeach,
        softCoral = softCoral,
        darkCharcoal = darkCharcoal,
        mediumGray = mediumGray,
        softMintBackground = softMintBackground,
        strongMintOverlay = strongMintOverlay,
        veryLightGray = veryLightGray,
        darkBackground = darkBackground,
        errorColor = errorColor,
        warningColor = warningColor,
        lightDivider = lightDivider,
        darkDivider = darkDivider
    )
}

fun colorsSet(
    white: Color = whiteColor,
    black: Color = blackColor,
    primaryGreen: Color = primaryGreenColor,
    lightGreen: Color = lightGreenColor,
    lightGrey: Color = lightGreyColor,
    secondaryPink: Color = secondaryPinkColor,
    lightCyanBlue: Color = lightCyanBlueColor,
    lightLimeGreen: Color = lightLimeGreenColor,
    softPeach: Color = softPeachColor,
    softCoral: Color = softCoralColor,
    darkCharcoal: Color = darkCharcoalColor,
    mediumGray: Color = mediumGrayColor,
    softMintBackground: Color = softMintBackgroundColor,
    strongMintOverlay: Color = strongMintOverlayColor,
    veryLightGray: Color = veryLightGrayColor,
    darkBackground: Color = darkBackgroundColor,
    errorColor: Color = errorColorColor,
    warningColor: Color = warningColorColor,
    lightDivider: Color = lightDividerColor,
    darkDivider: Color = darkDividerColor
): RecipeColors = RecipeColors(
    white = white,
    black = black,
    primaryGreen = primaryGreen,
    lightGreen = lightGreen,
    lightGrey = lightGrey,
    secondaryPink = secondaryPink,
    lightCyanBlue = lightCyanBlue,
    lightLimeGreen = lightLimeGreen,
    softPeach = softPeach,
    softCoral = softCoral,
    darkCharcoal = darkCharcoal,
    mediumGray = mediumGray,
    softMintBackground = softMintBackground,
    strongMintOverlay = strongMintOverlay,
    veryLightGray = veryLightGray,
    darkBackground = darkBackground,
    errorColor = errorColor,
    warningColor = warningColor,
    lightDivider = lightDivider,
    darkDivider = darkDivider
)

val LocalColors = staticCompositionLocalOf { colorsSet() }

val whiteColor: Color = Color(color = 0xFFFFFFFF)
val blackColor: Color = Color(color = 0xFF000000)

val primaryGreenColor: Color = Color(color = 0xFF93E9BE)
val lightGreenColor: Color = Color(color = 0xFFCFF5E7)

val lightGreyColor: Color = Color(color = 0xFFF5F5F5)

// Divider colors
val lightDividerColor: Color = Color(color = 0xFFE0E0E0)
val darkDividerColor: Color = Color(color = 0xFFCCCCCC)

// Secondary colors
val secondaryPinkColor: Color = Color(color = 0xFFE993B6)
val lightCyanBlueColor: Color = Color(color = 0xFF93D5E9)
val lightLimeGreenColor: Color = Color(color = 0xFFA2E993)
val softPeachColor: Color = Color(color = 0xFFE9BE93)
val softCoralColor: Color = Color(color = 0xFFE99393)

// For texts
val darkCharcoalColor: Color = Color(color = 0xFF333333) // primary
val mediumGrayColor: Color = Color(color = 0xFF666666) // secondary

// Button Colors
val softMintBackgroundColor: Color = Color(0x8093E9BE) // 50% opacity (alpha = 0x80)
val strongMintOverlayColor: Color = Color(0xCC93E9BE)  // 80% opacity (alpha = 0xCC)
// darkCharcoalColor for Secondary Button Background

// Screen Background colors
val veryLightGrayColor: Color = Color(color = 0xFFF7F7F7)
val darkBackgroundColor: Color = Color(color = 0xFF222222)

// error colors
val errorColorColor: Color = Color(color = 0xFFE99393)
val warningColorColor: Color = Color(color = 0xFFE9BE93)
