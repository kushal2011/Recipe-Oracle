package com.kdtech.recipeoracle.features.recipechat.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kdtech.recipeoracle.features.recipechat.presentation.models.RecipeChatState
import com.kdtech.recipeoracle.features.recipechat.presentation.viewmodel.RecipeChatViewModel
import com.kdtech.recipeoracle.resources.DrawableResources
import com.kdtech.recipeoracle.resources.theme.RecipeTheme

@Composable
fun RecipeChatScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeChatViewModel
) {
    val state by viewModel.state.collectAsState(RecipeChatState())
    val lazyColumnListState = rememberLazyListState()

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (topAppBar, chatListing, chatTextField) = createRefs()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(topAppBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
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
                style = RecipeTheme.typography.robotoMedium,
                color = RecipeTheme.colors.black100
            )
        }
        LazyColumn(
            modifier = Modifier
                .constrainAs(chatListing) {
                    top.linkTo(topAppBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            state = lazyColumnListState
        ) {
            itemsIndexed(state.chatList) { index, item ->
                ChatBubbleCard(
                    chatBubbleState = item
                )
            }
        }
        ChatTextField(
            modifier = Modifier.constrainAs(chatTextField) {
              start.linkTo(parent.start)
              end.linkTo(parent.end)
              bottom.linkTo(parent.bottom)
            },
            onSendClicked = viewModel::sendMessage,
        )
    }
}