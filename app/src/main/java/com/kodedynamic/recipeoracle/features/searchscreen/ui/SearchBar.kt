package com.kodedynamic.recipeoracle.features.searchscreen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.kodedynamic.recipeoracle.common.Empty
import com.kodedynamic.recipeoracle.resources.DrawableResources
import com.kodedynamic.recipeoracle.resources.components.drawableId
import com.kodedynamic.recipeoracle.resources.theme.RecipeTheme

@Composable
fun SearchBar(
    text: String,
    onValueChange: (value: String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier,
    searchModifier: Modifier = Modifier,
    keyboardController: SoftwareKeyboardController?= null,
    onCloseClick: () -> Unit = {}
) {
    var showClearButton by remember { mutableStateOf(false) }
    showClearButton = text.isNotEmpty()
    val searchIcon = DrawableResources.searchIcon

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Unspecified,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.Gray
        ),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        TextField(
            value = text,
            onValueChange = {
                onValueChange(it)
                showClearButton = it.isNotEmpty()
            },
            placeholder = { Text(text = placeholderText) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(size = 25.dp),
            textStyle = RecipeTheme.typography.robotoMedium,
            modifier = searchModifier,
            colors = TextFieldDefaults.colors(
                cursorColor = RecipeTheme.colors.primaryGreen,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = searchIcon),
                    contentDescription = "Search",
                    tint = RecipeTheme.colors.darkCharcoal,
                    modifier = Modifier
                        .semantics { drawableId = searchIcon }
                        .padding(start = 8.dp, top = 4.dp, end = 2.dp, bottom = 4.dp)
                        .size(20.dp)
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = showClearButton,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(
                        onClick = {
                            if (text.isNotEmpty()) {
                                onValueChange(String.Empty)
                                showClearButton = false
                            }
                            onCloseClick()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = DrawableResources.closeIcon),
                            contentDescription = "close",
                            modifier = Modifier
                                .padding(12.dp)
                                .size(16.dp)
                        )
                    }
                }
            }
        )
    }
}