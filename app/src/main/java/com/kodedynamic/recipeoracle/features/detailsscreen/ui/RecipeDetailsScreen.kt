package com.kodedynamic.recipeoracle.features.detailsscreen.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodedynamic.recipeoracle.apis.domain.models.IngredientsModel
import com.kodedynamic.recipeoracle.apis.domain.models.RecipeModel
import com.kodedynamic.recipeoracle.common.Empty
import com.kodedynamic.recipeoracle.features.detailsscreen.presentation.models.RecipeDetailsState
import com.kodedynamic.recipeoracle.features.detailsscreen.presentation.viewmodel.RecipeDetailsViewmodel
import com.kodedynamic.recipeoracle.resources.DrawableResources
import com.kodedynamic.recipeoracle.resources.StringResources
import com.kodedynamic.recipeoracle.resources.components.LottieLoader
import com.kodedynamic.recipeoracle.resources.components.RemoteImage
import com.kodedynamic.recipeoracle.resources.theme.RecipeTheme

@Composable
fun RecipeDetailsScreen(
    viewModel: RecipeDetailsViewmodel,
    modifier: Modifier = Modifier
) {
    val ingredientViewExpanded = rememberSaveable { mutableStateOf(false) }
    val methodViewExpanded = rememberSaveable { mutableStateOf(true) }

    val state by viewModel.state.collectAsState(initial = RecipeDetailsState())

    val context = LocalContext.current

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(RecipeTheme.colors.lightGrey),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = viewModel::onBackPress,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = DrawableResources.back),
                    contentDescription = "Go Back",
                    tint = RecipeTheme.colors.darkCharcoal,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = state.recipeData?.recipeName ?: String.Empty,
                style = RecipeTheme.typography.headerMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = RecipeTheme.colors.darkCharcoal,
                modifier = Modifier
                    .weight(1f)
            )
            IconButton(
                onClick = {
                    val searchTerm = "${state.recipeData?.recipeName} recipe"
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                            context.getString(
                                StringResources.youtubeQuery,
                                searchTerm
                            )
                        )
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            ) {
                Icon(
                    painter = painterResource(id = DrawableResources.play),
                    contentDescription = "Go to YouTube",
                    tint = RecipeTheme.colors.darkCharcoal,
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(
                onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, viewModel.getRecipeShareText())
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                }
            ) {
                Icon(
                    painter = painterResource(id = DrawableResources.share),
                    contentDescription = "Share",
                    tint = RecipeTheme.colors.darkCharcoal,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        state.recipeData?.let { recipeData ->
            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                item {
                    RemoteImage(
                        imageUrl = recipeData.imageUrl,
                        contentDescription = "Recipe image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(vertical = 16.dp),
                        placeholderRes = DrawableResources.recipeItemPlaceholder
                    )
                }
                item {
                    Text(
                        text = recipeData.recipeName,
                        color = RecipeTheme.colors.darkCharcoal,
                        style = RecipeTheme.typography.headerMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                item {
                    RecipeDetailsCardList(recipeData = recipeData)
                }

                item {
                    Button(
                        onClick = {
                            viewModel.onChatClick(recipeData.recipeName)
                        },
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
            LottieLoader()
        }
    }
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