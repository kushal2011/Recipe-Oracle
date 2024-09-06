package com.kodedynamic.recipeoracle.apis.data.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.kodedynamic.recipeoracle.apis.data.models.CategoriesDto
import com.kodedynamic.recipeoracle.apis.data.models.HomeFeedWidgetsDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefStorageHelper @Inject constructor(
    private val prefs: SharedPreferences
) {
    private val gson = Gson()

    fun saveLocalVersion(key: String, version: Long) {
        prefs.edit().putLong(key, version).apply()
    }

    fun getLocalVersion(key: String): Long {
        return prefs.getLong(key, 0L)
    }

    fun saveHomeFeed(key: String, homeFeedWidgetsDto: HomeFeedWidgetsDto) {
        prefs.edit().putString(key, gson.toJson(homeFeedWidgetsDto)).apply()
    }

    fun getHomeFeed(key: String): HomeFeedWidgetsDto? {
        val jsonString = prefs.getString(key, null)
        return gson.fromJson(jsonString, HomeFeedWidgetsDto::class.java)
    }

    fun saveCategories(key: String, categoriesDto: CategoriesDto) {
        prefs.edit().putString(key, gson.toJson(categoriesDto)).apply()
    }

    fun getCategories(key: String): CategoriesDto? {
        val jsonString = prefs.getString(key, null)
        return gson.fromJson(jsonString, CategoriesDto::class.java)
    }
}