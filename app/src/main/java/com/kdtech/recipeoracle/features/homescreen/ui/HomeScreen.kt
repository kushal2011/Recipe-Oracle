package com.kdtech.recipeoracle.features.homescreen.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.kdtech.recipeoracle.features.homescreen.presentation.models.HomeState
import com.kdtech.recipeoracle.features.homescreen.presentation.viewmodel.HomeViewModel
import com.kdtech.recipeoracle.resources.compositions.RecipeCard

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    val state by viewModel.state.collectAsState(HomeState())
    val lazyColumnListState = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        state = lazyColumnListState
    ) {
        itemsIndexed(state.recipeList) { _, item ->
            RecipeCard(
                recipeTitle = item.name,
                recipeMakingTime = item.prepTime,
                recipeImageUrl = "https://www.indianhealthyrecipes.com/wp-content/uploads/2014/11/paneer-butter-masala-recipe-2.jpg",
                onClick = viewModel::onDetailsClick
            )
            HorizontalDivider()
        }
    }
}