package com.aptopayments.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

interface ConnectivityChecker {
    fun isConnected(): Boolean
}

class ConnectivityCheckerImpl(context: Context) : ConnectivityChecker {
    private val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isConnected() =
        cm.getNetworkCapabilities(cm.activeNetwork)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
}
