package com.kdtech.recipeoracle.common

import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarDuration

sealed class ScreenEvent {
    class ShowToast(@StringRes val resourceId: Int = 0, val message: String = String.Empty) : ScreenEvent()
    class ShowSnackBar(
        val message: String = String.Empty,
        val actionLabel: String = String.Empty,
        val duration: SnackbarDuration = SnackbarDuration.Short
    ) : ScreenEvent()
    object None : ScreenEvent()
}
