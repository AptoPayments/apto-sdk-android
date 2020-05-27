package com.aptopayments.core.repository.user.remote.entities

import com.aptopayments.core.data.user.notificationpreferences.NotificationPreferences
import com.aptopayments.core.network.ListEntity
import com.google.gson.annotations.SerializedName

internal data class NotificationPreferencesEntity(

    @SerializedName("preferences")
    val preferences: ListEntity<NotificationGroupEntity>? = null

) {
    fun toNotificationPreferences() = NotificationPreferences(
        preferences = if (preferences?.data?.isEmpty() == false) preferences.data?.map { it.toNotificationGroup() } else null
    )
}
