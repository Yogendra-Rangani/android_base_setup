package com.example.android_base.application
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import com.example.android_base.retrofit.ApiResponseHandler.Companion.setNetworkConnectivityStatus
import com.example.android_base.utils.toast
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationManager : Application() {
    private lateinit var networkChangeCallback: ConnectivityManager.NetworkCallback

    override fun onCreate() {
        super.onCreate()
        registerNetworkCallback()
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterNetworkCallback()
    }

    private fun registerNetworkCallback() {
        networkChangeCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                setNetworkConnectivityStatus(networkAvailable = true)
                "App is online now".toast(applicationContext)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                setNetworkConnectivityStatus(networkAvailable = false)
                "App is offline now".toast(applicationContext)
            }
        }

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(networkRequest, networkChangeCallback)
    }

    private fun unregisterNetworkCallback() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(networkChangeCallback)
    }
}
