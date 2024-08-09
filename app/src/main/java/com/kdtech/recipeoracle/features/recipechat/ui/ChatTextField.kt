
package com.kdtech.recipeoracle.features.recipechat.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.kdtech.recipeoracle.common.Empty
import com.kdtech.recipeoracle.resources.DrawableResources
import com.kdtech.recipeoracle.resources.theme.RecipeTheme
import com.kdtech.recipeoracle.resources.theme.toWidthDp

@Composable
fun ChatTextField(
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    border: BorderStroke? = null,
    onSendClicked: (String) -> Unit = {},
    backgroundColor: Color = Color.LightGray
) {
    val focusRequester = remember { FocusRequester() }
    var text by remember {
        mutableStateOf(
            TextFieldValue(String.Empty)
        )
    }
    Card(
        modifier = modifier,
        colors = CardColors(
            containerColor = backgroundColor,
            contentColor = Color.Unspecified,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.Gray
        ),
        shape = RoundedCornerShape(24.dp),
        border = border,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(start = 18.dp, end = 0.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            TextField(
                modifier = Modifier
                    .width(80F.toWidthDp())
                    .focusRequester(focusRequester),
                value = text,
                maxLines = maxLines,
                textStyle = RecipeTheme.typography.robotoMedium,
                onValueChange = {
                    text = it
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Default
                ),
                colors = TextFieldDefaults.colors(
                    backgroundColor
                )
            )
            Spacer(Modifier.width(8.dp))
            IconButton(
                onClick = {
                    onSendClicked(text.text)
                    text = TextFieldValue(String.Empty)
                }
            ) {
                Icon(
                    painter = painterResource(id = DrawableResources.sendIcon),
                    contentDescription = "Send Message",
                    tint = RecipeTheme.colors.primaryGreen
                )
            }
        }
    }
}