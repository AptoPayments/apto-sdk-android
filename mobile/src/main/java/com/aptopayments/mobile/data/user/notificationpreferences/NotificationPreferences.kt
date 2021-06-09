package com.aptopayments.mobile.data.user.notificationpreferences

import java.io.Serializable

data class NotificationPreferences(
    val preferences: List<NotificationGroup>
) : Serializable
