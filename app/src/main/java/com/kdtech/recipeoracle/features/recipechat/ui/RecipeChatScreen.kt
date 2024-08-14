package com.kdtech.recipeoracle.features.recipechat.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kdtech.recipeoracle.features.recipechat.presentation.models.RecipeChatState
import com.kdtech.recipeoracle.features.recipechat.presentation.viewmodel.RecipeChatViewModel
import com.kdtech.recipeoracle.resources.DrawableResources
import com.kdtech.recipeoracle.resources.StringResources
import com.kdtech.recipeoracle.resources.theme.RecipeTheme
import com.kdtech.recipeoracle.resources.theme.toHeightDp
import kotlinx.coroutines.delay

@Composable
fun RecipeChatScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeChatViewModel
) {
    val state by viewModel.state.collectAsState(RecipeChatState())
    val lazyColumnListState = rememberLazyListState()

    val checkingRecipeText: String = stringResource(StringResources.checkingRecipeBook)
    var typingText by remember { mutableStateOf(checkingRecipeText) }
    val typingDots = listOf("", ".", "..", "...")

    LaunchedEffect(state.chatList.size) {
        if (state.chatList.isNotEmpty()) {
            lazyColumnListState.animateScrollToItem(state.chatList.size - 1)
        }
    }

    LaunchedEffect(state.typingIndicator) {
        if (state.typingIndicator) {
            var dotIndex = 0
            while (state.typingIndicator) {
                typingText = "$checkingRecipeText${typingDots[dotIndex % typingDots.size]}"
                dotIndex++
                delay(500L) // Delay to simulate typing effect
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = viewModel::onBackPress
            ) {
                Icon(
                    painter = painterResource(id = DrawableResources.back),
                    contentDescription = "Back",
                    tint = RecipeTheme.colors.black100
                )
            }
            Text(
                text = "Chat With Ai",
                style = RecipeTheme.typography.headerMedium,
                color = RecipeTheme.colors.black100
            )
        }
        Spacer(modifier = Modifier.height(8.toHeightDp()))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = lazyColumnListState
        ) {
            itemsIndexed(state.chatList) { index, item ->
                ChatBubbleCard(
                    chatBubbleState = item
                )
            }
            if (state.typingIndicator) {
                item {
                    Text(
                        text = typingText,
                        modifier = Modifier
                            .padding(8.dp)
                            .animateContentSize(),
                        style = RecipeTheme.typography.bodyRegular,
                        color = RecipeTheme.colors.mediumGray
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.toHeightDp()))
        ChatTextField(
            modifier = Modifier
                .fillMaxWidth(),
            onSendClicked = viewModel::sendMessage,
        )
    }
}