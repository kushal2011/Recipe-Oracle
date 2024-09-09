package com.kodedynamic.recipeoracle.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

enum class ConnectivityStatus {
    Available,
    Unavailable,
    Losing,
    Lost
}

interface ConnectivityObserver {
    fun observe(): Flow<ConnectivityStatus>
}

@Singleton
class NetworkConnectivityObserver @Inject constructor(
    context: Context
) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkStatusChannel = Channel<ConnectivityStatus>()

    init {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(
            networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    networkStatusChannel.trySend(ConnectivityStatus.Available)
                }

                override fun onLost(network: Network) {
                    networkStatusChannel.trySend(ConnectivityStatus.Lost)
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    networkStatusChannel.trySend(ConnectivityStatus.Losing)
                }

                override fun onUnavailable() {
                    networkStatusChannel.trySend(ConnectivityStatus.Unavailable)
                }
            }
        )
    }

    override fun observe(): Flow<ConnectivityStatus> = networkStatusChannel.receiveAsFlow()
}
