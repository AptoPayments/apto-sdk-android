package com.aptopayments.mobile.network

class ConnectivityCheckerAlwaysConnected : ConnectivityChecker {

    override fun isConnected() = true
}
