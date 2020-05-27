package com.aptopayments.core.network

class ConnectivityCheckerAlwaysConnected : ConnectivityChecker {

    override fun isConnected() = true
}
