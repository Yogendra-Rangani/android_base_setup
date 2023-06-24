package com.example.android_base.utils.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.example.android_base.retrofit.ApiResponseHandler.Companion.setNetworkConnectivityStatus
import com.example.android_base.utils.toast

class NetworkChangeReceiver : BroadcastReceiver() {
    var wasConnected = 0
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            val isConnected = networkInfo != null && networkInfo.isConnected
            when {
                isConnected && wasConnected == 0 -> {
                    // Internet is available
                    wasConnected = 1
                    setNetworkConnectivityStatus(networkAvailable = true)
                }
                isConnected && wasConnected == 1 -> {
                    // Internet is reconnected
                    setNetworkConnectivityStatus(networkAvailable = true)
                    "App is online now".toast(context)
                }
                else -> {
                    // Internet is not available
                    wasConnected = 1
                    setNetworkConnectivityStatus(networkAvailable = false)
                    "App is offline now".toast(context)
                }
            }
        }
    }
}

