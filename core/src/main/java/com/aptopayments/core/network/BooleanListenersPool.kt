package com.aptopayments.core.network

class BooleanListenersPool {

    private var listeners: MutableMap<Any, (Boolean) -> Unit> = mutableMapOf()
    private val listenersLock = Any()

    fun registerListener(instance: Any, callback: (Boolean) -> Unit) {
        synchronized(listenersLock) { listeners[instance] = callback }
    }

    fun unregisterListener(instance: Any) {
        synchronized(listenersLock) { listeners.remove(instance) }
    }

    fun notifyListeners(available: Boolean) {
        synchronized(listenersLock) {
            listeners.iterator().forEach { it.value.invoke(available) }
        }
    }
}
