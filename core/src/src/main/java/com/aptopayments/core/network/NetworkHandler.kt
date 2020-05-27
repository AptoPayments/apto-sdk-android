package com.aptopayments.core.network

import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket

/**
 * Injectable class which returns information about the network connection state.
 */
class NetworkHandler(private val connectivityChecker: ConnectivityChecker) {

    private val networkReachabilityListenerPool = BooleanListenersPool()
    private val maintenanceModeListenerPool = BooleanListenersPool()
    private val deprecatedSdkListenerPool = BooleanListenersPool()

    val isConnected get() = connectivityChecker.isConnected()

    fun subscribeNetworkReachabilityListener(instance: Any, callback: (Boolean) -> Unit) =
        networkReachabilityListenerPool.registerListener(instance, callback)

    fun unsubscribeNetworkReachabilityListener(instance: Any) =
        networkReachabilityListenerPool.unregisterListener(instance)

    fun networkNotReachable() =
        networkReachabilityListenerPool.notifyListeners(available = false)

    fun checkNetworkReachability(host: String, port: Int) {
        try {
            Socket().use { socket ->
                val inetAddress = InetAddress.getByName(host)
                val inetSocketAddress = InetSocketAddress(inetAddress, port)
                socket.connect(inetSocketAddress, 2000)
                networkReachabilityListenerPool.notifyListeners(available = true)
            }
        } catch (e: java.io.IOException) {
        }
    }

    fun subscribeMaintenanceListener(instance: Any, callback: (Boolean) -> Unit) =
        maintenanceModeListenerPool.registerListener(instance, callback)

    fun unsubscribeMaintenanceListener(instance: Any) =
        maintenanceModeListenerPool.unregisterListener(instance)

    fun maintenanceModeDetected() =
        maintenanceModeListenerPool.notifyListeners(available = false)

    fun checkMaintenanceMode() =
        maintenanceModeListenerPool.notifyListeners(available = true)

    fun subscribeDeprecatedSdkListener(instance: Any, callback: (Boolean) -> Unit) =
        deprecatedSdkListenerPool.registerListener(instance, callback)

    fun deprecatedSdkDetected() =
        deprecatedSdkListenerPool.notifyListeners(true)
}
