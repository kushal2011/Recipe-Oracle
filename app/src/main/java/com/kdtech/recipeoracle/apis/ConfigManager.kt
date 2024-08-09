package com.kdtech.recipeoracle.apis

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
                minimumFetchIntervalInSeconds = 36000
            }
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(mapOf(KEY_HOME_FEED_VERSION to DEFAULT_HOME_FEED_VERSION))
        }
    }

    suspend fun fetchHomeFeedVersion(): Long {
        return runCatching {
            remoteConfig.fetchAndActivate().await()
            remoteConfig.getLong(KEY_HOME_FEED_VERSION)
        }.getOrElse { DEFAULT_HOME_FEED_VERSION }
    }

    companion object {
        private const val KEY_HOME_FEED_VERSION = "home_feed_version"
        private const val DEFAULT_HOME_FEED_VERSION = 1L
    }
}