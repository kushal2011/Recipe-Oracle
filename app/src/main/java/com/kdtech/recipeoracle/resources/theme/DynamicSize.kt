package com.kdtech.recipeoracle.resources.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val NUMBER_100 = 100
private const val HEIGHT_800 = 800
private const val WIDTH_360 = 360

/**
 * Converts pixel value (Float) to Dp
 */
@Composable
fun Float.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

/**
 * Gets Width of device screen
 */
@Composable
private fun getScreenWidthInPx(): Float {
    return with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }
}

/**
 * Gets Height of device screen
 */
@Composable
private fun getScreenHeightInPx(): Float {
    return with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }
}

/**
 * Converts px value to Dp
 * @param widthFromFigma total screen width of figma , generally it should be 360
 */
@Composable
fun Int.toWidthDp(
    widthFromFigma: Int = WIDTH_360
): Dp {
    return ((this * getScreenWidthInPx()) / widthFromFigma).pxToDp()
}

/**
 * Converts px value to Dp
 * @param heightFromFigma total screen height of figma , generally it should be 800
 */
@Composable
fun Int.toHeightDp(
    heightFromFigma: Int = HEIGHT_800
): Dp {
    return ((this * getScreenHeightInPx()) / heightFromFigma).pxToDp()
}

/**
 * Gets dp from input , eg: we call 20f.toWidthDp() it will return 20% width of screen in dp
 */
@Composable
fun Float.toWidthDp(): Dp {
    return ((this / NUMBER_100) * getScreenWidthInPx()).pxToDp()
}

/**
 * Gets dp from input , eg: we call 20f.toHeightDp() it will return 20% height of screen in dp
 */
@Composable
fun Float.toHeightDp(): Dp {
    return ((this / NUMBER_100) * getScreenHeightInPx()).pxToDp()
}