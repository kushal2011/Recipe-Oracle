package com.kdtech.recipeoracle.apis.data.networks

import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kdtech.recipeoracle.BuildConfig
import com.kdtech.recipeoracle.apis.data.local.PrefStorageHelper
import com.kdtech.recipeoracle.apis.data.models.HomeFeedWidgetsDto
import com.kdtech.recipeoracle.apis.data.models.RecipeDto
import com.kdtech.recipeoracle.apis.domain.models.RecipeRequestModel
import com.kdtech.recipeoracle.common.ConvertToObject
import com.kdtech.recipeoracle.prompt.Prompts
import javax.inject.Inject

class RecipesDataSourceImpl @Inject constructor(
    private val prefStorageHelper: PrefStorageHelper,
    private val recipesApi: RecipesApi
) : RecipesDataSource {
    override suspend fun getRecipes(prompt: String): Result<List<RecipeDto>> {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.GEMENI_API_KEY
        )
        val response = generativeModel.generateContent(prompt)
        val recipesList: List<RecipeDto>? = response.text?.ConvertToObject()
        recipesList?.let {
            prefStorageHelper.saveList(PREF_KEY, recipesList)
            return Result.success(recipesList)
        }?: run {
            val throwable = Throwable(response.toString())
            FirebaseCrashlytics.getInstance().recordException(throwable)
            return Result.failure(throwable)
        }
    }

    override suspend fun getLocallyStoredRecipes(): Result<List<RecipeDto>> {
        val recipesList = prefStorageHelper.getList(PREF_KEY)
        return if (recipesList.isNotEmpty()) {
            Result.success(recipesList)
        } else {
            getRecipes(
                Prompts.getPromptForRecipes(RecipeRequestModel())
            )
        }
    }

    override suspend fun getHomeFeedDataFromRemote(configVersion: Long): Result<HomeFeedWidgetsDto> {
        val result = safeApiCall(
            {
                recipesApi.getHomeFeedData()
            },
            ::Exception
        )
        if (result.isFailure) {
            return result
        } else {
            result.getOrNull()?.let {
                prefStorageHelper.saveHomeFeed(PREF_KEY_FOR_HOME_FEED, it)
                prefStorageHelper.saveLocalHomeFeedVersion(PREF_KEY_FOR_HOME_FEED_VERSION, configVersion)
            }
            return result
        }
    }

    override suspend fun getHomeFeedDataFromLocal(configVersion: Long): Result<HomeFeedWidgetsDto> {
        val homeFeedWidgetsDto = prefStorageHelper.getHomeFeed(PREF_KEY_FOR_HOME_FEED)
        homeFeedWidgetsDto?.let {
            return Result.success(it)
        }?: run {
            return getHomeFeedDataFromRemote(configVersion)
        }
    }

    override suspend fun getHomeFeedData(configVersion: Long): Result<HomeFeedWidgetsDto> {
        val localVersion = prefStorageHelper.getLocalHomeFeedVersion(PREF_KEY_FOR_HOME_FEED_VERSION)
        return if (configVersion > localVersion) {
            getHomeFeedDataFromRemote(configVersion)
        } else {
            getHomeFeedDataFromLocal(configVersion)
        }
    }

    companion object {
        private const val PREF_KEY = "recipes_list"
        private const val PREF_KEY_FOR_HOME_FEED = "homefeed"
        private const val PREF_KEY_FOR_HOME_FEED_VERSION = "homefeed_version"
    }
}