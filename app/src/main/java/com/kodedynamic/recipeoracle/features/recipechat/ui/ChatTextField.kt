
package com.kodedynamic.recipeoracle.features.recipechat.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.kodedynamic.recipeoracle.common.Empty
import com.kodedynamic.recipeoracle.resources.DrawableResources
import com.kodedynamic.recipeoracle.resources.StringResources
import com.kodedynamic.recipeoracle.resources.theme.RecipeTheme

@Composable
fun ChatTextField(
    modifier: Modifier = Modifier,
    maxLines: Int = 4,
    shouldEnableSend: Boolean,
    onSendClicked: (String) -> Unit = {},
    backgroundColor: Color = Color.White
) {
    val focusRequester = remember { FocusRequester() }
    var text by remember {
        mutableStateOf(
            TextFieldValue(String.Empty)
        )
    }
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
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
        Row(
            modifier = Modifier
                .padding(start = 18.dp, end = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            TextField(
                modifier = Modifier
                    .weight(1f)
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
                    cursorColor = RecipeTheme.colors.primaryGreen,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = stringResource(StringResources.typeAMessage),
                        style = RecipeTheme.typography.robotoMedium,
                        color = RecipeTheme.colors.mediumGray
                    )
                }
            )
            Spacer(Modifier.width(8.dp))
            IconButton(
                onClick = {
                    onSendClicked(text.text)
                    text = TextFieldValue(String.Empty)
                },
                enabled = shouldEnableSend && text.text.isNotEmpty()
            ) {
                Icon(
                    painter = painterResource(id = DrawableResources.sendIcon),
                    contentDescription = "Send Message",
                    tint = if (shouldEnableSend && text.text.isNotEmpty()) {
                        RecipeTheme.colors.primaryGreen
                    } else {
                        RecipeTheme.colors.mediumGray
                    }
                )
            }
        }
    }
}
