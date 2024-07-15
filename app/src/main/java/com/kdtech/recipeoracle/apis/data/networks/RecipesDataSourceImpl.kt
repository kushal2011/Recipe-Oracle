package com.kdtech.recipeoracle.apis.data.networks

import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kdtech.recipeoracle.BuildConfig
import com.kdtech.recipeoracle.apis.data.models.RecipeModel
import com.kdtech.recipeoracle.common.ConvertToObject
import javax.inject.Inject

class RecipesDataSourceImpl @Inject constructor() : RecipesDataSource {
    override suspend fun getRecipes(prompt: String): Result<List<RecipeModel>> {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.GEMENI_API_KEY
        )
        val response = generativeModel.generateContent(prompt)
        val recipesList: List<RecipeModel>? = response.text?.ConvertToObject()
        recipesList?.let {
            return Result.success(recipesList)
        }?: run {
            val throwable = Throwable(response.toString())
            FirebaseCrashlytics.getInstance().recordException(throwable)
            return Result.failure(throwable)
        }
    }
}