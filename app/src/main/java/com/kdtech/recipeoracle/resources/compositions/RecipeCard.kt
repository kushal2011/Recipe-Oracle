package com.kdtech.recipeoracle.resources.compositions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kdtech.recipeoracle.common.Empty
import com.kdtech.recipeoracle.resources.ContentDescriptionResources
import com.kdtech.recipeoracle.resources.DrawableResources
import com.kdtech.recipeoracle.resources.components.RemoteImage
import com.kdtech.recipeoracle.resources.theme.RecipeTheme
import com.kdtech.recipeoracle.resources.theme.toHeightDp
import com.kdtech.recipeoracle.resources.theme.toWidthDp

@Composable
fun RecipeCard(
    recipeTitle: String,
    recipeMakingTime: String,
    recipeImageUrl: String = String.Empty
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(150.toWidthDp())
            .height(150.toHeightDp()),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Box {
            RemoteImage(
                imageUrl = recipeImageUrl,
                contentDescription = ContentDescriptionResources.RECIPE_IMAGE,
                modifier = Modifier.fillMaxSize()
            )
            Row(
                modifier = Modifier
                    .align(
                        alignment = Alignment.TopEnd
                    )
                    .padding(6.dp)
            ) {
                Image(
                    painter = painterResource(id = DrawableResources.clockIcon),
                    contentDescription = ContentDescriptionResources.CLOCK
                )
                Text(
                    color = Color.White,
                    text = recipeMakingTime,
                    style = RecipeTheme.typography.robotoMedium,
                )
            }

            Text(
                text = recipeTitle,
                color = Color.White,
                style = RecipeTheme.typography.robotoBold,
                modifier = Modifier
                    .align(
                        alignment = Alignment.BottomStart
                    )
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                RecipeTheme.colors.transparent,
                                RecipeTheme.colors.black100
                            ),
                            startY = 0f,
                            endY = 100f
                        )
                    )
                    .padding(6.dp)
            )
        }
    }
}