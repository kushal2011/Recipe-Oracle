package com.kodedynamic.recipeoracle.apis.data.networks

import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.JsonParser
import com.kodedynamic.recipeoracle.BuildConfig
import com.kodedynamic.recipeoracle.apis.data.local.PrefStorageHelper
import com.kodedynamic.recipeoracle.apis.data.models.CategoriesDto
import com.kodedynamic.recipeoracle.apis.data.models.HomeFeedWidgetsDto
import com.kodedynamic.recipeoracle.apis.data.models.OpenAiChatDto
import com.kodedynamic.recipeoracle.apis.data.models.OpenAiChatRequestDto
import com.kodedynamic.recipeoracle.apis.data.models.RecipeDto
import com.kodedynamic.recipeoracle.apis.data.models.RecipeListDto
import com.kodedynamic.recipeoracle.apis.domain.models.SeeAllRecipeRequest
import com.kodedynamic.recipeoracle.common.ConvertToObject
import javax.inject.Inject

private const val GEMINI_1_5_FLASH_MODEL_NAME = "gemini-1.5-flash"

class RecipesDataSourceImpl @Inject constructor(
    private val prefStorageHelper: PrefStorageHelper,
    private val recipesApi: RecipesApi
) : RecipesDataSource {
    override suspend fun getRecipes(prompt: String): Result<RecipeListDto> {
        return runCatching {
            val generativeModel = GenerativeModel(
                modelName = GEMINI_1_5_FLASH_MODEL_NAME,
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

    override suspend fun getRecipeById(recipeId: String): Result<RecipeDto> {
        return safeApiCall(
            {
                recipesApi.getRecipeById(recipeId)
            },
            ::Exception
        )
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
                prefStorageHelper.saveLocalVersion(PREF_KEY_FOR_HOME_FEED_VERSION, configVersion)
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
        val localVersion = prefStorageHelper.getLocalVersion(PREF_KEY_FOR_HOME_FEED_VERSION)
        return if (configVersion > localVersion) {
            getHomeFeedDataFromRemote(configVersion)
        } else {
            getHomeFeedDataFromLocal(configVersion)
        }
    }

    override suspend fun getCategoriesData(configVersion: Long): Result<CategoriesDto> {
        val localVersion = prefStorageHelper.getLocalVersion(PREF_KEY_FOR_CATEGORIES_VERSION)
        return if (configVersion > localVersion) {
            getCategoriesFromRemote(configVersion)
        } else {
            getCategoriesDataFromLocal(configVersion)
        }
    }

    override suspend fun getCategoriesFromRemote(configVersion: Long): Result<CategoriesDto> {
        val result = safeApiCall(
            {
                recipesApi.getCategories()
            },
            ::Exception
        )
        if (result.isFailure) {
            return result
        } else {
            result.getOrNull()?.let {
                prefStorageHelper.saveCategories(PREF_KEY_FOR_CATEGORIES, it)
                prefStorageHelper.saveLocalVersion(PREF_KEY_FOR_CATEGORIES_VERSION, configVersion)
            }
            return result
        }
    }

    override suspend fun getCategoriesDataFromLocal(configVersion: Long): Result<CategoriesDto> {
        val categoriesDto = prefStorageHelper.getCategories(PREF_KEY_FOR_CATEGORIES)
        categoriesDto?.let {
            return Result.success(it)
        }?: run {
            return getCategoriesFromRemote(configVersion)
        }
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

    override suspend fun postGeneratedRecipes(json: String): Result<Unit> {
        val jsonElement = JsonParser.parseString(json)
        val jsonObject = jsonElement.asJsonObject
        return safeApiCall(
            {
                recipesApi.postGeneratedRecipes(
                    body = jsonObject
                )
            },
            ::Exception
        )
    }

    override suspend fun chatWithOpenAi(chatRequestDto: OpenAiChatRequestDto): Result<OpenAiChatDto> {
        return safeApiCall(
            {
                recipesApi.openAiChatApi(
                    url = OPEN_AI_CHAT_URL,
                    body = chatRequestDto
                )
            },
            ::Exception
        )
    }

    companion object {
        private const val PREF_KEY_FOR_HOME_FEED = "homefeed"
        private const val PREF_KEY_FOR_CATEGORIES = "categories"
        private const val PREF_KEY_FOR_HOME_FEED_VERSION = "homefeed_version"
        private const val PREF_KEY_FOR_CATEGORIES_VERSION = "categories_version"
        private const val OPEN_AI_CHAT_URL = "https://api.openai.com/v1/chat/completions"
    }
}