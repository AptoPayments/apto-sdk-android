package com.aptopayments.core.network

import android.content.Context
import com.aptopayments.core.extension.networkInfo
import java.lang.ref.WeakReference
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Injectable class which returns information about the network connection state.
 */
@Singleton
class NetworkHandler
@Inject constructor(context: Context) {

    var context: WeakReference<Context> = WeakReference(context)
    val isConnected get() = context.get()?.networkInfo?.isConnectedOrConnecting


    private val networkReachabilityListenerPool = BooleanListenersPool()
    private val maintenanceModeListenerPool = BooleanListenersPool()
    private val deprecatedSdkListenerPool = BooleanListenersPool()

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
        } catch (e: java.io.IOException) {}
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
