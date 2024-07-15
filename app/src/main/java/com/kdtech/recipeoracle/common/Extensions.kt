package com.kdtech.recipeoracle.common

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val String.Companion.Empty
    inline get() = ""

inline fun <reified T> String.ConvertToObject(): T {
    val gson = Gson()
    return gson.fromJson(this, object : TypeToken<T>() {}.type)
}