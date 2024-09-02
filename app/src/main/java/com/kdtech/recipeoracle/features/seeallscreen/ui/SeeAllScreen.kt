package com.kdtech.recipeoracle.features.seeallscreen.ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.kdtech.recipeoracle.features.seeallscreen.presentation.models.SeeAllState
import com.kdtech.recipeoracle.features.seeallscreen.presentation.viewmodel.SeeAllViewModel
import com.kdtech.recipeoracle.resources.compositions.RecipeCard

@Composable
fun SeeAllScreen(
    viewModel: SeeAllViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState(SeeAllState())

    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        modifier = modifier,
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
}