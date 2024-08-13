package com.kdtech.recipeoracle.resources.compositions

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.kdtech.recipeoracle.resources.StringResources
import com.kdtech.recipeoracle.resources.theme.RecipeTheme

@Composable
fun DefaultConfirmDialog(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    dismissOnClickOutside: Boolean = true,
    leftButtonColor: Color = RecipeTheme.colors.darkCharcoal,
    rightButtonColor: Color = RecipeTheme.colors.primaryGreen,
    titleColor: Color = RecipeTheme.colors.darkCharcoal,
    descriptionColor: Color = RecipeTheme.colors.mediumGray,
    backgroundColor: Color = RecipeTheme.colors.lightGrey,
    shouldShowVerticalPadding: Boolean = true,
    isButtonSwapped: Boolean = false,
    leftText: String = stringResource(id = StringResources.cancel),
    onLeftClick: () -> Unit = {},
    rightText: String = stringResource(id = StringResources.ok),
    onRightClick: () -> Unit = {}
) {
    AlertDialog(
        modifier = modifier,
        containerColor = backgroundColor,
        shape = RoundedCornerShape(size = 8.dp),
        onDismissRequest = if (isButtonSwapped) {
            onLeftClick
        } else {
            onRightClick
        },
        title = {
            Text(
                text = title.ifEmpty { "Error" },
                style = RecipeTheme.typography.headerSemiBold,
                color = titleColor,
            )
        },
        text = {
            Text(
                text = message.ifEmpty { "Unknown error" },
                style = RecipeTheme.typography.bodyRegular,
                color = descriptionColor,
                modifier = if (shouldShowVerticalPadding) Modifier.padding(vertical = 40.dp) else Modifier
            )
        },
        confirmButton = {
            TextButton(
                onClick = onRightClick
            ) {
                Text(
                    text = rightText,
                    style = RecipeTheme.typography.buttonSemiBold,
                    color = rightButtonColor,
                    textAlign = TextAlign.Center,
                )
            }
        },
        dismissButton = {
            if (leftText.isNotEmpty()) {
                TextButton(
                    onClick = onLeftClick
                ) {
                    Text(
                        text = leftText,
                        style = RecipeTheme.typography.buttonSemiBold,
                        color = leftButtonColor,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        },
        properties = DialogProperties(dismissOnClickOutside = dismissOnClickOutside)
    )
}