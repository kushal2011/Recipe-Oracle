package com.kodedynamic.recipeoracle.features.forceupdate.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kodedynamic.recipeoracle.R
import com.kodedynamic.recipeoracle.resources.StringResources
import com.kodedynamic.recipeoracle.resources.components.LottieLoader
import com.kodedynamic.recipeoracle.resources.theme.RecipeTheme
import com.kodedynamic.recipeoracle.resources.theme.toWidthDp

private const val APP_URL = "https://play.google.com/store/apps/details?id=com.kodedynamic.recipeoracle"
@Composable
fun ForceUpdateScreen(
    modifier: Modifier = Modifier,
    title: String
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = AbsoluteAlignment.Left
    ) {
        LottieLoader(
            modifier = modifier.weight(1f),
            loaderSize = 80f.toWidthDp(),
            loaderJson = R.raw.update_app
        )
        Text(
            text = title,
            color = RecipeTheme.colors.darkCharcoal,
            style = RecipeTheme.typography.headerMedium,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
        Button(
            onClick = {
                uriHandler.openUri(APP_URL)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = RecipeTheme.colors.primaryGreen
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(StringResources.updateApp),
                color = RecipeTheme.colors.white100,
                style = RecipeTheme.typography.buttonSemiBold
            )
        }
    }
}