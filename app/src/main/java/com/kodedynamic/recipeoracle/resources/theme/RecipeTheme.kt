package com.kodedynamic.recipeoracle.resources.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember

object RecipeTheme {
    val typography: RecipeTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val colors: RecipeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}

@Composable
fun RecipeTheme(
    colors: RecipeColors = RecipeTheme.colors,
    typography: RecipeTypography = RecipeTheme.typography,
    content: @Composable () -> Unit
) {
    val rememberedColors = remember { colors.copy() }
    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalTypography provides typography
    ) {
        content()
    }
}