package com.kodedynamic.recipeoracle.features.recipechat.presentation.models

import com.kodedynamic.recipeoracle.common.Empty
import com.kodedynamic.recipeoracle.common.ScreenEvent
import com.kodedynamic.recipeoracle.common.State

data class RecipeChatState(
    override val id: String = String.Empty,
    var chatList: List<MessageModel> = emptyList(),
    val typingIndicator: Boolean = false,
    val screenEvent: ScreenEvent = ScreenEvent.None
) : State