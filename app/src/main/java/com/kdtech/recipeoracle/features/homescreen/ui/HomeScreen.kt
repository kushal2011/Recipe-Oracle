package com.kdtech.recipeoracle.features.homescreen.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.kdtech.recipeoracle.features.homescreen.presentation.models.HomeState
import com.kdtech.recipeoracle.features.homescreen.presentation.viewmodel.HomeViewModel

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
            Text(text = item.name)
            Text(text = item.prepTime)
            Text(text = "isVegan: ${item.isVegan}")
            Text(text = "isVegetarian: ${item.isVegetarian}")
            Text(text = "isNonVeg: ${item.isNonVeg}")
            Text(text = "isJain: ${item.isJain}")
            Text(text = "isEggiterian: ${item.isEggiterian}")
            HorizontalDivider()
        }
    }
}