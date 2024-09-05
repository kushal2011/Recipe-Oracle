package com.kdtech.recipeoracle.features.homescreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kdtech.recipeoracle.common.ScreenEvent
import com.kdtech.recipeoracle.common.toast
import com.kdtech.recipeoracle.features.homescreen.presentation.models.HomeState
import com.kdtech.recipeoracle.features.homescreen.presentation.viewmodel.HomeViewModel
import com.kdtech.recipeoracle.resources.components.LottieLoader
import com.kdtech.recipeoracle.resources.compositions.RecipeCard
import com.kdtech.recipeoracle.resources.theme.RecipeTheme
import com.kdtech.recipeoracle.resources.theme.toHeightDp
import com.kdtech.recipeoracle.resources.theme.toWidthDp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    val state by viewModel.state.collectAsState(HomeState())
    val lazyColumnListState = rememberLazyListState()

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

    LazyColumn(
        modifier = modifier.padding(16.dp),
        state = lazyColumnListState
    ) {
        itemsIndexed(state.homeFeedWidgets) { _, widget ->
            val lazyRowListState = rememberLazyListState()
            Row {
                Text(
                    text = widget.title,
                    style = RecipeTheme.typography.robotoBold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.clickable {
                        viewModel.onSeeAllClick(
                            widget.widgetId,
                            widget.title
                        )
                    },
                    text = "See all",
                    style = RecipeTheme.typography.robotoMedium,
                    color = RecipeTheme.colors.black100
                )
            }
            LazyRow(
                modifier = modifier,
                state = lazyRowListState
            ) {
                itemsIndexed(widget.recipesList) { _, item ->
                    RecipeCard(
                        recipeTitle = item.recipeName,
                        recipeMakingTime = " ${item.prepTime} Minutes",
                        recipeImageUrl = item.imageUrl,
                        onClick = {
                            viewModel.onDetailsClick(it, widget.widgetId )
                        },
                        recipeId = item.recipeId
                    )
                    Spacer(modifier = Modifier.width(16.toWidthDp()))
                }
            }
            Spacer(modifier = Modifier.height(8.toHeightDp()))
        }
    }

    if (state.isLoading) {
        LottieLoader()
    }
}