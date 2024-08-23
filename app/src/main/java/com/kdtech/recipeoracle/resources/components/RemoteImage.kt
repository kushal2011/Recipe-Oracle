package com.kdtech.recipeoracle.resources.components

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kdtech.recipeoracle.resources.DrawableResources

@Composable
fun RemoteImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    bitmap: Bitmap? = null,
    contentScale: ContentScale = ContentScale.FillBounds,
    @DrawableRes placeholderRes: Int = DrawableResources.recipeItemPlaceholder,
    onImageLoadSuccess: () -> Unit = {},
    onImageLoadFailure: () -> Unit = {}
) {
    val context = LocalContext.current

    val imageRequest = ImageRequest.Builder(context)
        .data(bitmap ?: imageUrl)
        .allowRgb565(true) // Use a lower color depth to reduce memory usage
        .memoryCachePolicy(CachePolicy.ENABLED) // Enable memory caching for better performance
        .diskCachePolicy(CachePolicy.ENABLED) // Enable disk caching for offline support
        .networkCachePolicy(CachePolicy.ENABLED) // Cache on the network level to avoid unnecessary requests
        .crossfade(true) // Smooth transition between placeholder and image
        .placeholder(placeholderRes)
        .build()

    AsyncImage(
        model = imageRequest,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        onSuccess = { onImageLoadSuccess() },
        onError = {
            onImageLoadFailure()
            FirebaseCrashlytics.getInstance().recordException(it.result.throwable)
        }
    )
}
