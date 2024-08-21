package com.kdtech.recipeoracle.features.detailsscreen.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kdtech.recipeoracle.apis.domain.models.IngredientsModel
import com.kdtech.recipeoracle.apis.domain.models.RecipeModel
import com.kdtech.recipeoracle.common.Empty
import com.kdtech.recipeoracle.features.detailsscreen.presentation.models.RecipeDetailsState
import com.kdtech.recipeoracle.features.detailsscreen.presentation.viewmodel.RecipeDetailsViewmodel
import com.kdtech.recipeoracle.resources.DrawableResources
import com.kdtech.recipeoracle.resources.StringResources
import com.kdtech.recipeoracle.resources.components.RemoteImage
import com.kdtech.recipeoracle.resources.theme.RecipeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    viewModel: RecipeDetailsViewmodel,
    modifier: Modifier = Modifier
) {
    val ingredientViewExpanded = rememberSaveable { mutableStateOf(false) }
    val methodViewExpanded = rememberSaveable { mutableStateOf(true) }

    val state by viewModel.state.collectAsState(initial = RecipeDetailsState())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.recipeData?.recipeName ?: String.Empty,
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
        },
        content = { innerPadding ->
            val contentModifier = modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = 0.dp // Ignore the bottom padding
                )
                .padding(horizontal = 16.dp)
                .fillMaxSize()

            state.recipeData?.let { recipeData ->
                LazyColumn(modifier = contentModifier) {
                    item {
                        RemoteImage(
                            imageUrl = recipeData.imageUrl.ifEmpty {
                                "https://www.indianhealthyrecipes.com/wp-content/uploads/2014/11/paneer-butter-masala-recipe-2.jpg"
                            },
                            contentDescription = "Recipe image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(vertical = 16.dp)
                        )
                    }

                    item {
                        RecipeDetailsCardList(recipeData = recipeData)
                    }

                    item {
                        Button(
                            onClick = { /* Handle AI Chat */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = RecipeTheme.colors.primaryGreen
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        ) {
                            Text(
                                text = stringResource(StringResources.chatWithAi),
                                color = RecipeTheme.colors.white100,
                                style = RecipeTheme.typography.buttonSemiBold
                            )
                        }
                    }


                    item {
                        ExpandableCard(
                            title = stringResource(StringResources.ingredients),
                            isExpanded = ingredientViewExpanded.value,
                            onClick = {
                                ingredientViewExpanded.value = !ingredientViewExpanded.value
                            },
                            content = {
                                Column {
                                    recipeData.ingredients.forEach { ingredient ->
                                        IngredientItem(ingredient = ingredient)
                                    }
                                }
                            }
                        )
                    }

                    item {
                        ExpandableCard(
                            title = stringResource(StringResources.method),
                            isExpanded = methodViewExpanded.value,
                            onClick = { methodViewExpanded.value = !methodViewExpanded.value },
                            content = {
                                Column {
                                    recipeData.instructions.forEach { instruction ->
                                        BulletPointText(instruction.step)
                                    }
                                }
                            }
                        )
                    }
                }
            } ?: run {
                // Show loading or placeholder content here
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeDetailsCardList(recipeData: RecipeModel) {
    FlowRow(
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        CategoryCard(
            text = stringResource(
                StringResources.prepTimesInMins,
                recipeData.prepTime
            ),
            paddingEnd = 16.dp
        )

        CategoryCard(
            text = stringResource(
                StringResources.cuisine,
                recipeData.cuisineType
            ),
            paddingEnd = 16.dp
        )

        CategoryCard(
            text = stringResource(
                StringResources.course,
                recipeData.course
            ),
            paddingEnd = 16.dp
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    FlowRow {
        if (recipeData.isVegetarian) {
            CategoryCard(text = stringResource(StringResources.vegetarian))
        }
        if (recipeData.isNonVeg) {
            CategoryCard(text = stringResource(StringResources.nonVegetarian))
        }
        if (recipeData.isEggiterian) {
            CategoryCard(text = stringResource(StringResources.eggitarian))
        }
        if (recipeData.isJain) {
            CategoryCard(text = stringResource(StringResources.jain))
        }
        if (recipeData.isVegan) {
            CategoryCard(text = stringResource(StringResources.vegan))
        }
    }
}

@Composable
fun CategoryCard(
    text: String,
    paddingEnd: Dp = 8.dp,
    paddingBottom: Dp = 8.dp
) {
    Text(
        text = text,
        color = RecipeTheme.colors.darkCharcoal,
        style = RecipeTheme.typography.bodyRegularSmall,
        modifier = Modifier.padding(
            end = paddingEnd,
            bottom = paddingBottom
        )
    )
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
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = RecipeTheme.colors.veryLightGray
        ),
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
                    color = RecipeTheme.colors.darkCharcoal,
                    modifier = Modifier.padding(5.dp),
                    style = RecipeTheme.typography.headerMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(
                        id = if (isExpanded) {
                            DrawableResources.upArrow
                        } else {
                            DrawableResources.downArrow
                        }
                    ),
                    contentDescription = if (isExpanded) {
                        "Collapse"
                    } else {
                        "Expand"
                    },
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
        Text(
            text = ingredient.ingredientName,
            color = RecipeTheme.colors.darkCharcoal,
            style = RecipeTheme.typography.bodyRegular
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = ingredient.quantity,
            color = RecipeTheme.colors.mediumGray,
            style = RecipeTheme.typography.bodyRegular
        )
    }
}

@Composable
fun BulletPointText(text: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = ParagraphStyle(
                    textIndent = TextIndent(
                        firstLine = 12.sp,
                        restLine = 22.sp
                    )
                )
            ) {
                append("• ")
                append(text)
            }
        },
        color = RecipeTheme.colors.darkCharcoal,
        modifier = Modifier.padding(10.dp),
        style = RecipeTheme.typography.bodyRegular.copy(
            lineHeight = 22.sp
        )
    )
}