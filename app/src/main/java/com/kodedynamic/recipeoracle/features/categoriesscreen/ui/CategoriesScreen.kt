package com.kodedynamic.recipeoracle.features.categoriesscreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kodedynamic.recipeoracle.common.Empty
import com.kodedynamic.recipeoracle.common.ScreenEvent
import com.kodedynamic.recipeoracle.common.toast
import com.kodedynamic.recipeoracle.features.categoriesscreen.presentation.models.CategoriesState
import com.kodedynamic.recipeoracle.features.categoriesscreen.presentation.viewmodel.CategoriesViewModel
import com.kodedynamic.recipeoracle.resources.components.LottieLoader
import com.kodedynamic.recipeoracle.resources.components.RemoteImage
import com.kodedynamic.recipeoracle.resources.theme.RecipeTheme

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel
) {
    val state by viewModel.state.collectAsState(CategoriesState())

    val lazyGridState = rememberLazyGridState()

    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.screenEvent) {
        val event = state.screenEvent
        if (event is ScreenEvent.ShowToast) {
            context.toast(message = event.message, resourceId = event.resourceId)
            viewModel.onScreenEventsShown()
        } else if (event is ScreenEvent.ShowSnackBar) {
            snackBarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.actionLabel,
                duration = event.duration
            )
            viewModel.onScreenEventsShown()
        }
    }

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        state = lazyGridState
    ) {
        itemsIndexed(state.cuisines) { index, item ->
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onClick = {
                    viewModel.onCuisineClick(item.cuisineType)
                }
            ) {
                Box {
                    // Image with gradient overlay
                    Box(
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    ) {
                        RemoteImage(
                            imageUrl = item.imageUrl,
                            contentDescription = String.Empty,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(Color.Black, Color.Transparent),
                                        center = Offset(x = 1f, y = 1f),
                                        radius = 2000f
                                    )
                                )
                        )
                    }

                    // Centered text
                    Text(
                        text = item.cuisineType,
                        modifier = Modifier.align(Alignment.Center),
                        color = RecipeTheme.colors.white100,
                        style = RecipeTheme.typography.headerSemiBold
                    )
                }
            }
        }
    }

    if (state.isLoading) {
        LottieLoader()
    }
}