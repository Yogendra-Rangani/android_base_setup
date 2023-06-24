package com.example.android_base.application

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.example.android_base.utils.service.NetworkChangeReceiver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationManager : Application() {
    private lateinit var networkChangeReceiver: NetworkChangeReceiver
    override fun onCreate() {
        super.onCreate()
        networkChangeReceiver = NetworkChangeReceiver()
        registerNetworkChangeReceiver()
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterNetworkChangeReceiver()
    }

    private fun registerNetworkChangeReceiver() {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, intentFilter)
    }

    private fun unregisterNetworkChangeReceiver() {
        unregisterReceiver(networkChangeReceiver)
    }

}