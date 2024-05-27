package com.kdtech.recipeoracle.features.homescreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kdtech.recipeoracle.features.homescreen.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    Column {
        Text(text = "This is home screen")
        Button(onClick = {
            viewModel.onDetailsClick()
        }) {
            Text(text = "details")
        }
    }

}