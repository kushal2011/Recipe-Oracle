package com.kdtech.recipeoracle.features.recipechat.presentation.models

import com.kdtech.recipeoracle.common.Empty
import com.kdtech.recipeoracle.common.ScreenEvent
import com.kdtech.recipeoracle.common.State

data class RecipeChatState(
    override val id: String = String.Empty,
    var chatList: List<MessageModel> = emptyList(),
    val typingIndicator: Boolean = false,
    val screenEvent: ScreenEvent = ScreenEvent.None
) : State