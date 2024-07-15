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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kdtech.recipeoracle.R
import com.kdtech.recipeoracle.common.Empty
import com.kdtech.recipeoracle.resources.components.RemoteImage

@Composable
fun RecipeCard(
    recipeTitle: String,
    recipeMakingTime: String,
    recipeImageUrl: String = String.Empty
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)
            .height(150.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Box {
            RemoteImage(
                imageUrl = recipeImageUrl,
                contentDescription = "recipe image",
                modifier = Modifier.fillMaxSize()
            )
            Row(
                modifier = Modifier
                    .align(
                        alignment = androidx.compose.ui.Alignment.TopEnd
                    )
                    .padding(6.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_access_time_filled_24),
                    contentDescription = "clock"
                )
                Text(
                    color = Color.White,
                    text = recipeMakingTime,
                    style = MaterialTheme.typography.bodyMedium,
                )

            }

            Text(
                text = recipeTitle,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(
                        alignment = androidx.compose.ui.Alignment.BottomStart
                    )
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
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