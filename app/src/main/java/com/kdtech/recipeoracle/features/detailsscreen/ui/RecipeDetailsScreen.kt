package com.kdtech.recipeoracle.features.detailsscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kdtech.recipeoracle.apis.domain.models.IngredientsModel
import com.kdtech.recipeoracle.apis.domain.models.RecipeModel
import com.kdtech.recipeoracle.features.detailsscreen.presentation.models.RecipeDetailsState
import com.kdtech.recipeoracle.features.detailsscreen.presentation.viewmodel.RecipeDetailsViewmodel
import com.kdtech.recipeoracle.resources.DrawableResources
import com.kdtech.recipeoracle.resources.components.RemoteImage
import com.kdtech.recipeoracle.resources.theme.RecipeTheme
import com.kdtech.recipeoracle.resources.theme.toHeightDp
import com.kdtech.recipeoracle.resources.theme.toWidthDp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    viewModel: RecipeDetailsViewmodel,
    modifier: Modifier = Modifier
) {
    val ingredientViewExpanded = rememberSaveable { mutableStateOf(false) }
    val methodViewExpanded = rememberSaveable { mutableStateOf(false) }

    val state by viewModel.state.collectAsState(initial = RecipeDetailsState())

    state.recipeData?.let { recipeData ->
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            item {
                TopAppBar(
                    title = {
                        Text(
                            text = recipeData.recipeName,
                            style = MaterialTheme.typography.titleLarge
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
                        IconButton(onClick = { /* Handle back navigation */ }) {
                            Icon(
                                painter = painterResource(id = DrawableResources.back),
                                contentDescription = "Go Back",
                                tint = RecipeTheme.colors.darkCharcoal,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Handle YouTube navigation */ }) {
                            Icon(
                                painter = painterResource(id = DrawableResources.play),
                                contentDescription = "Go to YouTube",
                                tint = RecipeTheme.colors.darkCharcoal,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        IconButton(onClick = { /* Handle share action */ }) {
                            Icon(
                                painter = painterResource(id = DrawableResources.share),
                                contentDescription = "Share",
                                tint = RecipeTheme.colors.darkCharcoal,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    modifier = Modifier.statusBarsPadding()
                )
            }

            item {
                RemoteImage(
                    imageUrl = recipeData.imageUrl,
                    contentDescription = "Recipe image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp)
                )
            }

            item {
                RecipeDetailsCardList(recipeData = recipeData)
            }

            item {
                ExpandableCard(
                    title = "Ingredients",
                    isExpanded = ingredientViewExpanded.value,
                    onClick = { ingredientViewExpanded.value = !ingredientViewExpanded.value }
                ) {
                    Column {
                        recipeData.ingredients.forEach { ingredient ->
                            IngredientItem(ingredient = ingredient)
                        }
                    }
                }
            }

            item {
                ExpandableCard(
                    title = "Method",
                    isExpanded = methodViewExpanded.value,
                    onClick = { methodViewExpanded.value = !methodViewExpanded.value }
                ) {
                    Column {
                        recipeData.instructions.forEach { instruction ->
                            Text(
                                text = instruction.step,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = { /* Handle AI Chat */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Chat with AI")
                }
            }
        }
    } ?: run {
        // Show loading or placeholder content here
    }
}

@Composable
fun RecipeDetailsCardList(recipeData: RecipeModel) {
    Column {
        CategoryCard(
            image = DrawableResources.timeBlack,
            type = "Prep Time: ${recipeData.prepTime} mins"
        )

        CategoryCard(
            image = DrawableResources.cuisine,
            type = "Cuisine: ${recipeData.cuisineType}"
        )

        CategoryCard(
            image = DrawableResources.courseType,
            type = "Course: ${recipeData.course}"
        )

        when {
            recipeData.isVegetarian -> CategoryCard(image = DrawableResources.veg, type = "Veg")
            recipeData.isNonVeg -> CategoryCard(image = DrawableResources.nonVeg, type = "Non-Veg")
            recipeData.isEggiterian -> CategoryCard(image = DrawableResources.eggitarian, type = "Eggitarian")
        }
    }
}

@Composable
fun CategoryCard(image: Int, type: String) {
    Row(modifier = Modifier.padding(5.dp)) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Category Icon",
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = type, color = Color.Black)
    }
}

@Composable
fun ExpandableCard(
    title: String,
    isExpanded: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    modifier = Modifier.padding(5.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = if (isExpanded) DrawableResources.upArrow else DrawableResources.downArrow),
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    modifier = Modifier.size(16.dp)
                )
            }
            if (isExpanded) {
                content()
            }
        }
    }
}

@Composable
fun IngredientItem(ingredient: IngredientsModel) {
    Row(modifier = Modifier.padding(10.dp)) {
        Text(text = ingredient.ingredientName)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = ingredient.quantity)
    }
}
