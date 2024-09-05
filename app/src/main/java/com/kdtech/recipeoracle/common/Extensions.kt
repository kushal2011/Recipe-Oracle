package com.kdtech.recipeoracle.common

import android.content.Context
import android.content.res.Resources
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val DEFAULT_TOAST_TEXT_SIZE = 14

val String.Companion.Empty
    inline get() = ""

inline fun <reified T> String.ConvertToObject(): T {
    val gson = Gson()
    return gson.fromJson(this, object : TypeToken<T>() {}.type)
}

fun Context.toast(
    resourceId: Int? = 0,
    message: String? = String.Empty,
    length: Int = Toast.LENGTH_LONG,
    fontSize: Int = DEFAULT_TOAST_TEXT_SIZE
) {
    if (message.isNullOrEmpty()) {
        val resId = resourceId ?: 0
        if (resId != 0) {
            Toast.makeText(this, fontSizeToast(fontSize, getString(resId)), length).show()
        } else {
            Log.e("Toast message","Else condition reached in toast extension with message: $resourceId")
        }
    } else {
        Toast.makeText(this, fontSizeToast(fontSize, message), length).show()
    }
}

fun Context.fontSizeToast(fontSize: Int, mess: String): SpannableString {
    val ssMessage = SpannableString(mess)
    ssMessage.setSpan(AbsoluteSizeSpan(spToPix(fontSize)), 0, ssMessage.length, 0)
    return ssMessage
}

fun Context.spToPix(sp: Int): Int = (sp * Resources.getSystem().displayMetrics.scaledDensity).toInt()
