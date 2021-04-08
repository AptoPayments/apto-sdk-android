package com.aptopayments.mobile.repository.user.remote.entities

import com.aptopayments.mobile.data.user.notificationpreferences.NotificationPreferences
import com.aptopayments.mobile.network.ListEntity
import com.google.gson.annotations.SerializedName

internal data class NotificationPreferencesEntity(

    @SerializedName("preferences")
    val preferences: ListEntity<NotificationGroupEntity>? = null

) {
    fun toNotificationPreferences() = NotificationPreferences(
        preferences = if (preferences?.data?.isEmpty() == false)
            preferences.data?.map { it.toNotificationGroup() }
        else
            null
    )
}
