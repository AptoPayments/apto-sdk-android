package com.aptopayments.mobile.repository.user.remote.entities

import com.aptopayments.mobile.data.user.notificationpreferences.ActiveChannels
import com.aptopayments.mobile.data.user.notificationpreferences.NotificationChannel
import com.google.gson.annotations.SerializedName

internal data class ActiveChannelsEntity(

    @SerializedName("email")
    val email: Boolean? = null,

    @SerializedName("push")
    val push: Boolean? = null,

    @SerializedName("sms")
    val sms: Boolean? = null

) {
    fun toActiveChannels(): ActiveChannels {
        val activeChannelsMap = hashMapOf(
            NotificationChannel.EMAIL to email,
            NotificationChannel.PUSH to push,
            NotificationChannel.SMS to sms
        )
        return ActiveChannels(activeChannelsMap = activeChannelsMap)
    }

    companion object {
        fun from(activeChannels: ActiveChannels): ActiveChannelsEntity {
            return ActiveChannelsEntity(
                email = activeChannels[NotificationChannel.EMAIL],
                push = activeChannels[NotificationChannel.PUSH],
                sms = activeChannels[NotificationChannel.SMS]
            )
        }
    }
}
