package com.kodedynamic.recipeoracle.resources.components

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
import com.kodedynamic.recipeoracle.resources.DrawableResources

private const val CLOUD_NAME = "ddlqixrjj"
private const val IMAGE_TRANSFORMATIONS = "f_auto,q_auto,c_fill,fl_progressive"
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

    // Construct the Cloudinary fetch URL
    val cloudinaryBaseUrl = "https://res.cloudinary.com/$CLOUD_NAME/image/fetch/"

    val optimizedUrl = "$cloudinaryBaseUrl$IMAGE_TRANSFORMATIONS/$imageUrl"

    val imageRequest = ImageRequest.Builder(context)
        .data(bitmap ?: optimizedUrl)
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
