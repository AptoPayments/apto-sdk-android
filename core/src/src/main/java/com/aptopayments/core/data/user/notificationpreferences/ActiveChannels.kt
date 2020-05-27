package com.aptopayments.core.data.user.notificationpreferences

import java.io.Serializable

data class ActiveChannels(
    private val activeChannelsMap: HashMap<NotificationChannel, Boolean?>
) : Serializable {

    operator fun get(channel: NotificationChannel): Boolean? = activeChannelsMap[channel]

    operator fun set(channel: NotificationChannel, value: Boolean?) {
        activeChannelsMap[channel] = value
    }

    fun isChannelActive(channel: NotificationChannel): Boolean {
        return activeChannelsMap[channel] ?: false
    }
}
