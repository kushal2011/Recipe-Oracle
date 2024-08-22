package com.kdtech.recipeoracle.features.categoriesscreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kdtech.recipeoracle.features.categoriesscreen.presentation.viewmodel.CategoriesViewModel
import com.kdtech.recipeoracle.resources.DrawableResources
import com.kdtech.recipeoracle.resources.theme.RecipeTheme

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel
) {
    Column {
        Text(text = "Categories Screen")
        Spacer(modifier = Modifier.height(25.dp))
        IconButton(onClick = viewModel::onBackPress) {
            Icon(
                painter = painterResource(id = DrawableResources.back),
                contentDescription = "Go Back",
                tint = RecipeTheme.colors.darkCharcoal,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}