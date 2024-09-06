package com.kodedynamic.recipeoracle.resources.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kodedynamic.recipeoracle.R

private val Roboto = FontFamily(
    Font(R.font.roboto_bold, FontWeight.W700),
    Font(R.font.roboto_regular, FontWeight.W400),
    Font(R.font.roboto_semi_bold, FontWeight.W600),
    Font(R.font.roboto_medium, FontWeight.W500)
)

private val Montserrat = FontFamily(
    Font(R.font.montserrat_bold, FontWeight.W700),
    Font(R.font.montserrat_regular, FontWeight.W400),
    Font(R.font.montserrat_semi_bold, FontWeight.W600),
    Font(R.font.montserrat_medium, FontWeight.W500)
)

data class RecipeTypography(
    // Headers
    val headerBold: TextStyle = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    val headerSemiBold: TextStyle = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    val headerMedium: TextStyle = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),

    // Subtitles
    val subtitleBold: TextStyle = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    val subtitleSemiBold: TextStyle = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),

    // Body text
    val bodyRegular: TextStyle = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    val bodyRegularSmall: TextStyle = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),

    // Captions and Overlines
    val captionRegular: TextStyle = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    val overlineMedium: TextStyle = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    ),

    // Buttons
    val buttonSemiBold: TextStyle = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),

    // Existing styles
    val robotoBold: TextStyle = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    val robotoMedium: TextStyle = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    )
)

internal val LocalTypography = staticCompositionLocalOf { RecipeTypography() }
