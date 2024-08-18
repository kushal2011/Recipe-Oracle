package com.kdtech.recipeoracle.features.detailsscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
    val ingredientViewExpanded = remember { mutableStateOf(false) }
    val methodViewExpanded = remember { mutableStateOf(false) }

    val state by viewModel.state.collectAsState(RecipeDetailsState())

    if (state.recipeData != null) {
        Column(modifier = modifier) {
            TopAppBar(
                title = {
                    Text(
                        text = state.recipeData!!.recipeName,
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
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = DrawableResources.back),
                            contentDescription = "Go Back",
                            tint = RecipeTheme.colors.darkCharcoal,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = DrawableResources.play),
                            contentDescription = "Go to Youtube",
                            tint = RecipeTheme.colors.darkCharcoal,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(onClick = { }) {
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

            Column(modifier = Modifier.padding(16.dp)) {
                RemoteImage(
                    imageUrl = state.recipeData!!.imageUrl,
                    contentDescription = "Recipe image",
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                )

                Column(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)) {
                    CategoryCard(
                        image = DrawableResources.timeBlack,
                        type = "Prep Time: ${state.recipeData!!.prepTime} mins"
                    )

                    CategoryCard(
                        image = DrawableResources.cuisine,
                        type = "Cuisine: ${state.recipeData!!.cuisineType}"
                    )

                    CategoryCard(
                        image = DrawableResources.courseType,
                        type = "Course: ${state.recipeData!!.course}"
                    )
                    if (state.recipeData!!.isVegetarian) {
                        CategoryCard(image = DrawableResources.veg, type = "Veg")
                    } else if (state.recipeData!!.isNonVeg) {
                        CategoryCard(image = DrawableResources.nonVeg, type = "Non-Veg")
                    } else if (state.recipeData!!.isEggiterian) {
                        CategoryCard(image = DrawableResources.eggitarian, type = "Eggitarian")
                    }
                }


//            else if (state.recipeData!!.isJain){
//                CategoryCard(image = R.drawable.ic_jain, type = "Jain")
//            }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 16.dp, 0.dp, 0.dp)
                        .clickable { ingredientViewExpanded.value = !ingredientViewExpanded.value },
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Column {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Ingredients", modifier = Modifier.padding(5.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            if (!ingredientViewExpanded.value) {
                                Image(
                                    painter = painterResource(id = DrawableResources.downArrow),
                                    contentDescription = "down arrow",
                                    modifier = Modifier
                                        .width(16.dp)
                                        .height(16.dp)
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = DrawableResources.downArrow),
                                    contentDescription = "down arrow",
                                    modifier = Modifier
                                        .width(16.dp)
                                        .height(16.dp)
                                )
                            }
                        }
                        LazyColumn {
                            itemsIndexed(state.recipeData!!.ingredients) { index, item ->
                                Row(modifier = Modifier.padding(10.dp)) {
                                    Text(text = item.ingredientName)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(text = item.quantity)
                                }
                            }
                        }
                    }

                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 16.dp, 0.dp, 0.dp)
                        .clickable { methodViewExpanded.value = !methodViewExpanded.value },
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Column {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Method", modifier = Modifier.padding(5.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))

                            if (!methodViewExpanded.value) {
                                Image(
                                    painter = painterResource(id = DrawableResources.downArrow),
                                    contentDescription = "down arrow",
                                    modifier = Modifier
                                        .width(16.dp)
                                        .height(16.dp)
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = DrawableResources.upArrow),
                                    contentDescription = "up arrow",
                                    modifier = Modifier
                                        .width(16.dp)
                                        .height(16.dp)
                                )
                            }
                        }
                        LazyColumn {
                            itemsIndexed(state.recipeData!!.instructions) { index, item ->
                                Text(
                                    text = item.step,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }

                }

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Text(text = "Chat with AI")
                }
            }
        }
    } else {
        // show loading or something
    }

}

@Composable
fun CategoryCard(image: Int, type: String) {
    Row(modifier = Modifier.padding(5.dp)) {
        Image(
            painterResource(id = image),
            contentDescription = "Chip Icon",
            modifier = Modifier
                .width(16.dp)
                .height(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = type, color = Color.Black
        )
    }
}