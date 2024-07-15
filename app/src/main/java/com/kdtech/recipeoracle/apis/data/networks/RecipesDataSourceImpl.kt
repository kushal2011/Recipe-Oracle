package com.kdtech.recipeoracle.apis.data.networks

import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kdtech.recipeoracle.BuildConfig
import com.kdtech.recipeoracle.apis.data.local.PrefStorageHelper
import com.kdtech.recipeoracle.apis.data.models.RecipeModel
import com.kdtech.recipeoracle.apis.data.models.RecipeRequestModel
import com.kdtech.recipeoracle.common.ConvertToObject
import com.kdtech.recipeoracle.prompt.Prompts
import javax.inject.Inject

class RecipesDataSourceImpl @Inject constructor(
    private val prefStorageHelper: PrefStorageHelper
) : RecipesDataSource {
    override suspend fun getRecipes(prompt: String): Result<List<RecipeModel>> {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.GEMENI_API_KEY
        )
        val response = generativeModel.generateContent(prompt)
        val recipesList: List<RecipeModel>? = response.text?.ConvertToObject()
        recipesList?.let {
            prefStorageHelper.saveList(PREF_KEY, recipesList)
            return Result.success(recipesList)
        }?: run {
            val throwable = Throwable(response.toString())
            FirebaseCrashlytics.getInstance().recordException(throwable)
            return Result.failure(throwable)
        }
    }

    override suspend fun getLocallyStoredRecipes(): Result<List<RecipeModel>> {
        val recipesList = prefStorageHelper.getList(PREF_KEY)
        return if (recipesList.isNotEmpty()) {
            Result.success(recipesList)
        } else {
            getRecipes(
                Prompts.getPromptForRecipes(RecipeRequestModel())
            )
        }
    }

    companion object {
        private const val PREF_KEY = "recipes_list"
    }
}