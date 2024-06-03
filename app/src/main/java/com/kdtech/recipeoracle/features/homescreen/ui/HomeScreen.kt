package com.kdtech.recipeoracle.features.homescreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Text(text = state.recipeText)
        Button(onClick = {
            viewModel.onDetailsClick()
        }) {
            Text(text = "details")
        }
    }

}