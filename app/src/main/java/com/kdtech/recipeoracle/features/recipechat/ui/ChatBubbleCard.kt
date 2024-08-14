package com.kdtech.recipeoracle.features.recipechat.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kdtech.recipeoracle.features.recipechat.presentation.models.MessageModel
import com.kdtech.recipeoracle.resources.theme.RecipeTheme
import com.kdtech.recipeoracle.resources.theme.toHeightDp
import com.kdtech.recipeoracle.resources.theme.toWidthDp
import io.noties.markwon.Markwon

@Composable
fun ChatBubbleCard(
    chatBubbleState: MessageModel,
    modifier: Modifier = Modifier
) {
    val arrangement: Arrangement.Horizontal
    val bubbleShape: RoundedCornerShape
    val bubbleColor: Color

    val context = LocalContext.current
    val markwon = remember { Markwon.create(context) }
    val spanned = remember { markwon.toMarkdown(chatBubbleState.message) }

    if (chatBubbleState.isMessageByUser) {
        arrangement = Arrangement.End
        bubbleShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 0.dp,
            bottomStart = 16.dp,
            bottomEnd = 16.dp
        )
        bubbleColor = RecipeTheme.colors.lightGreen
        }
    else {
        arrangement = Arrangement.Start
        bubbleShape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 16.dp,
            bottomStart = 16.dp,
            bottomEnd = 16.dp
        )
        bubbleColor = RecipeTheme.colors.lightGrey
    }

    Column(
        modifier = modifier
            .wrapContentHeight()
            .padding(bottom = 4.toHeightDp())
    ) {
        Row(
            horizontalArrangement = arrangement,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.wrapContentHeight()
        ) {
            if (chatBubbleState.isMessageByUser) {
                Spacer(modifier = Modifier.weight(1f))
            }
            Card(
                shape = bubbleShape,
                colors = CardColors(
                    contentColor = Color.Black,
                    containerColor = bubbleColor,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Gray
                ),
                modifier = Modifier
                    .widthIn(0.dp, 75F.toWidthDp())
                    .wrapContentSize(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 0.dp
                )
            ) {
                Column {
                    if (chatBubbleState.message.isNotEmpty()) {
                        Text(
                            text = spanned.toString(),
                            modifier = Modifier
                                .padding(
                                    horizontal = 12.toWidthDp(),
                                    vertical = 12.toHeightDp()
                                )
                                .wrapContentHeight(),
                        )
                    }
                }
            }
            if (!chatBubbleState.isMessageByUser) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

