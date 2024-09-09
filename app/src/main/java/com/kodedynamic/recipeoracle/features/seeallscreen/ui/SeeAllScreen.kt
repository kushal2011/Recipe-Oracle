package com.kodedynamic.recipeoracle.features.seeallscreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kodedynamic.recipeoracle.common.ScreenEvent
import com.kodedynamic.recipeoracle.common.toast
import com.kodedynamic.recipeoracle.features.seeallscreen.presentation.models.SeeAllState
import com.kodedynamic.recipeoracle.features.seeallscreen.presentation.viewmodel.SeeAllViewModel
import com.kodedynamic.recipeoracle.resources.DrawableResources
import com.kodedynamic.recipeoracle.resources.components.LottieLoader
import com.kodedynamic.recipeoracle.resources.compositions.RecipeCard
import com.kodedynamic.recipeoracle.resources.theme.RecipeTheme

@Composable
fun SeeAllScreen(
    viewModel: SeeAllViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState(SeeAllState())

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

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(RecipeTheme.colors.lightGrey),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = viewModel::onBackPress,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = DrawableResources.back),
                    contentDescription = "Go Back",
                    tint = RecipeTheme.colors.darkCharcoal,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = state.screenTitle,
                style = RecipeTheme.typography.headerMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = RecipeTheme.colors.darkCharcoal
            )
        }
        LazyVerticalGrid(
            modifier = Modifier,
            columns = GridCells.Fixed(2),
            state = lazyGridState
        ) {
            itemsIndexed(state.recipes) { index, item ->
                RecipeCard(
                    recipeTitle = item.recipeName,
                    recipeId = item.recipeId,
                    recipeMakingTime = " ${item.prepTime} Minutes",
                    recipeImageUrl = item.imageUrl,
                    onClick = viewModel::onDetailsClick
                )
            }
        }
        if (state.isLoading) {
            LottieLoader()
        }
    }
}