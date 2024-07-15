package com.kdtech.recipeoracle.resources.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kdtech.recipeoracle.R

private val Roboto = FontFamily(
    Font(R.font.roboto_bold, FontWeight.W700),
    Font(R.font.roboto_regular, FontWeight.W400),
    Font(R.font.roboto_semi_bold, FontWeight.W600),
    Font(R.font.roboto_medium, FontWeight.W500)
)
class RecipeTypography {
    val robotoBold: TextStyle = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W700,
        fontSize = 18.sp
    )
    val robotoMedium: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp
    )
}