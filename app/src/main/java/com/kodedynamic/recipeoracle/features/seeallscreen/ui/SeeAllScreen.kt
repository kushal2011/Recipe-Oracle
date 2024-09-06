package com.kodedynamic.recipeoracle.features.seeallscreen.ui

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kodedynamic.recipeoracle.common.ScreenEvent
import com.kodedynamic.recipeoracle.common.toast
import com.kodedynamic.recipeoracle.features.seeallscreen.presentation.models.SeeAllState
import com.kodedynamic.recipeoracle.features.seeallscreen.presentation.viewmodel.SeeAllViewModel
import com.kodedynamic.recipeoracle.resources.DrawableResources
import com.kodedynamic.recipeoracle.resources.components.LottieLoader
import com.kodedynamic.recipeoracle.resources.compositions.RecipeCard
import com.kodedynamic.recipeoracle.resources.theme.RecipeTheme

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.screenTitle,
                        style = RecipeTheme.typography.headerMedium
                    )
                },
                colors = TopAppBarColors(
                    containerColor = RecipeTheme.colors.lightGrey,
                    titleContentColor = RecipeTheme.colors.darkCharcoal,
                    navigationIconContentColor = RecipeTheme.colors.darkCharcoal,
                    actionIconContentColor = RecipeTheme.colors.darkCharcoal,
                    scrolledContainerColor = RecipeTheme.colors.primaryGreen
                ),
                navigationIcon = {
                    IconButton(onClick = viewModel::onBackPress) {
                        Icon(
                            painter = painterResource(id = DrawableResources.back),
                            contentDescription = "Go Back",
                            tint = RecipeTheme.colors.darkCharcoal,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                modifier = Modifier.statusBarsPadding()
            )
        },
        content = { innerPadding ->
        val contentModifier = modifier.padding(
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                top = innerPadding.calculateTopPadding(),
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                bottom = 0.dp // Ignore the bottom padding
            )
        LazyVerticalGrid(
            modifier =contentModifier,
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
    )
}