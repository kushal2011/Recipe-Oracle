package com.kdtech.recipeoracle.apis.data.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kdtech.recipeoracle.apis.data.models.RecipeDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefStorageHelper @Inject constructor(
    private val prefs: SharedPreferences
) {
    private val gson = Gson()
    private val listType = object : TypeToken<MutableList<RecipeDto>>() {}.type

    fun saveList(key: String, list: List<RecipeDto>) {
        val jsonString = gson.toJson(list)
        prefs.edit().putString(key, jsonString).apply()
    }

    fun getList(key: String): MutableList<RecipeDto> {
        val jsonString = prefs.getString(key, null) ?: return mutableListOf()
        return gson.fromJson(jsonString, listType)
    }

    fun appendToList(key: String, item: RecipeDto) {
        val list = getList(key)
        list.add(item)
        saveList(key, list)
    }

    fun removeFromList(key: String, item: RecipeDto) {
        val list = getList(key)
        list.remove(item)
        saveList(key, list)
    }
}