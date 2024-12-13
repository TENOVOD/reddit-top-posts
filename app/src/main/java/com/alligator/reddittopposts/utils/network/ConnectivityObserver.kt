package com.alligator.reddittopposts.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
   Class for monitoring network status
 **/
class ConnectivityObserver @Inject constructor(
    @ApplicationContext private val context: Context
) {
    enum class Status {
        Available, Unavailable, Undefined
    }

    private val _status = MutableStateFlow(Status.Undefined)
    val status: StateFlow<Status> = _status.asStateFlow()

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onUnavailable() {
            _status.value = Status.Unavailable
        }
        override fun onAvailable(network: Network) {
            _status.value = Status.Available
        }

        override fun onLost(network: Network) {
            _status.value = Status.Unavailable
        }

    }

    fun start() {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, networkCallback)
        val connected = isConnected()
        _status.value = if (connected) Status.Available else Status.Unavailable
    }

    fun stop() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
    private fun isConnected(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}