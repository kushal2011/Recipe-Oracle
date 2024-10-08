package com.kodedynamic.recipeoracle.apis

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigManager @Inject constructor() {

    private val remoteConfig by lazy {
        Firebase.remoteConfig.apply {
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            }
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(
                mapOf(
                    KEY_HOME_FEED_VERSION to DEFAULT_HOME_FEED_VERSION,
                    KEY_CATEGORIES_VERSION to DEFAULT_CATEGORIES_VERSION,
                    KEY_SHOULD_USE_GEMINI to DEFAULT_SHOULD_USE_GEMINI,
                    KEY_OPEN_AI_MODEL to DEFAULT_OPEN_AI_MODEL
                )
            )
        }
    }

    suspend fun fetchHomeFeedVersion(): Long {
        return runCatching {
            remoteConfig.fetchAndActivate().await()
            remoteConfig.getLong(KEY_HOME_FEED_VERSION)
        }.getOrElse { DEFAULT_HOME_FEED_VERSION }
    }

    suspend fun fetchCategoriesVersion(): Long {
        return runCatching {
            remoteConfig.fetchAndActivate().await()
            remoteConfig.getLong(KEY_CATEGORIES_VERSION)
        }.getOrElse { DEFAULT_CATEGORIES_VERSION }
    }

    suspend fun fetchShouldUseGemini(): Boolean {
        return runCatching {
            remoteConfig.fetchAndActivate().await()
            remoteConfig.getBoolean(KEY_SHOULD_USE_GEMINI)
        }.getOrElse { DEFAULT_SHOULD_USE_GEMINI }
    }

    suspend fun getOpenAiModel(): String {
        return runCatching {
            remoteConfig.fetchAndActivate().await()
            remoteConfig.getString(KEY_OPEN_AI_MODEL)
        }.getOrElse { DEFAULT_OPEN_AI_MODEL }
    }

    companion object {
        private const val KEY_HOME_FEED_VERSION = "HOME_FEED_VERSION"
        private const val KEY_CATEGORIES_VERSION = "CATEGORIES_VERSION"
        private const val KEY_SHOULD_USE_GEMINI = "KEY_SHOULD_USE_GEMINI"
        private const val KEY_OPEN_AI_MODEL = "key_open_ai_model"
        private const val DEFAULT_SHOULD_USE_GEMINI = true
        private const val DEFAULT_HOME_FEED_VERSION = 1L
        private const val DEFAULT_CATEGORIES_VERSION = 1L
        private const val DEFAULT_OPEN_AI_MODEL = "gpt-4o-2024-08-06"
    }
}