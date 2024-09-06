package com.kodedynamic.recipeoracle.apis.data.networks

import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kodedynamic.recipeoracle.BuildConfig
import com.kodedynamic.recipeoracle.apis.data.local.PrefStorageHelper
import com.kodedynamic.recipeoracle.apis.data.models.CategoriesDto
import com.kodedynamic.recipeoracle.apis.data.models.HomeFeedWidgetsDto
import com.kodedynamic.recipeoracle.apis.data.models.RecipeListDto
import com.kodedynamic.recipeoracle.apis.domain.models.SeeAllRecipeRequest
import com.kodedynamic.recipeoracle.common.ConvertToObject
import javax.inject.Inject

class RecipesDataSourceImpl @Inject constructor(
    private val prefStorageHelper: PrefStorageHelper,
    private val recipesApi: RecipesApi
) : RecipesDataSource {
    override suspend fun getRecipes(prompt: String): Result<RecipeListDto> {
        return runCatching {
            val generativeModel = GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = BuildConfig.GEMENI_API_KEY
            )

            val response = generativeModel.generateContent(prompt)
            val recipesList: RecipeListDto? = response.text?.ConvertToObject()
            recipesList ?: throw IllegalArgumentException("No recipes found for the provided prompt.")
        }.onFailure { throwable ->
            FirebaseCrashlytics.getInstance().recordException(throwable)
            Result.failure<Throwable>(throwable)
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

    override suspend fun getCategories(): Result<CategoriesDto> {
        return safeApiCall(
            {
                recipesApi.getCategories()
            },
            ::Exception
        )
    }

    override suspend fun getSeeAllRecipes(seeAllRecipeRequest: SeeAllRecipeRequest): Result<RecipeListDto> {
        return safeApiCall(
            {
                recipesApi.getSeeAllRecipes(
                    cuisineType = seeAllRecipeRequest.cuisineType,
                    prepTime = seeAllRecipeRequest.prepTime,
                    healthRating = seeAllRecipeRequest.healthRating,
                    topRated = seeAllRecipeRequest.topRated
                )
            },
            ::Exception
        )
    }

    override suspend fun getSearchedRecipes(searchText: String): Result<RecipeListDto> {
        return safeApiCall(
            {
                recipesApi.searchRecipes(
                    searchText = searchText
                )
            },
            ::Exception
        )
    }

    companion object {
        private const val PREF_KEY_FOR_HOME_FEED = "homefeed"
        private const val PREF_KEY_FOR_HOME_FEED_VERSION = "homefeed_version"
    }
}