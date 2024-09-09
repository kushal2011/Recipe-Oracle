package com.kodedynamic.recipeoracle.apis.data.repositories

import com.kodedynamic.recipeoracle.common.ConnectivityObserver
import com.kodedynamic.recipeoracle.common.ConnectivityStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
    private val connectivityObserver: ConnectivityObserver
) {
    fun getNetworkStatus(): Flow<ConnectivityStatus> {
        return connectivityObserver.observe()
    }
}