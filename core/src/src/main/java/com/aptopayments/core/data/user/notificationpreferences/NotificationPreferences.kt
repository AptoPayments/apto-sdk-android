package com.aptopayments.core.data.user.notificationpreferences

import java.io.Serializable

data class NotificationPreferences(
    val preferences: List<NotificationGroup>?
) : Serializable
