package com.kdtech.recipeoracle.resources.components

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.firebase.crashlytics.FirebaseCrashlytics

@Composable
fun RemoteImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    bitmap: Bitmap? = null,
    contentScale: ContentScale = ContentScale.FillBounds,
    @DrawableRes placeholderRes: Int? = null,
    onImageLoadSuccess: () -> Unit = {},
    onImageLoadFailure: () -> Unit = {}
) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(bitmap ?: imageUrl)
        .allowRgb565(true)
        .memoryCachePolicy(CachePolicy.DISABLED)
        .crossfade(true)
        .build()
    runCatching {
        AsyncImage(
            model = imageRequest,
            placeholder = placeholderRes?.let { painterResource(it) },
            error = placeholderRes?.let { painterResource(it) },
            modifier = modifier,
            contentDescription = contentDescription,
            contentScale = contentScale,
            onSuccess = { onImageLoadSuccess() },
            onError = { onImageLoadFailure() }
        )
    }.getOrElse {
        FirebaseCrashlytics.getInstance().recordException(it)
    }
}