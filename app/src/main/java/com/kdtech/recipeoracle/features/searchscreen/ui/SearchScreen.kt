package com.kdtech.recipeoracle.features.searchscreen.ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.kdtech.recipeoracle.common.ScreenEvent
import com.kdtech.recipeoracle.common.toast
import com.kdtech.recipeoracle.features.searchscreen.presentation.models.SearchState
import com.kdtech.recipeoracle.features.searchscreen.presentation.viewmodel.SearchViewModel
import com.kdtech.recipeoracle.resources.components.LottieLoader
import com.kdtech.recipeoracle.resources.compositions.RecipeCard

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel
) {
    val state by viewModel.state.collectAsState(SearchState())
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
        modifier =modifier,
        columns = GridCells.Fixed(2),
        state = lazyGridState
    ) {
        itemsIndexed(state.recipesList) { index, item ->
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