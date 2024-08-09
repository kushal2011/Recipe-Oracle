package com.kdtech.recipeoracle.features.recipechat.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.kdtech.recipeoracle.features.recipechat.presentation.models.RecipeChatState
import com.kdtech.recipeoracle.features.recipechat.presentation.viewmodel.RecipeChatViewModel

@Composable
fun RecipeChatScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeChatViewModel
) {
    val state by viewModel.state.collectAsState(RecipeChatState())
    val lazyColumnListState = rememberLazyListState()

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (chatListing, chatTextField) = createRefs()
        LazyColumn(
            modifier = Modifier
                .constrainAs(chatListing) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(chatTextField.top)
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