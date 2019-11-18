package com.aptopayments.core.data.user.notificationpreferences

import android.content.Context
import com.aptopayments.core.extension.localized
import java.io.Serializable

data class NotificationGroup(
        val categoryId: Category? = null,
        val groupId: Group? = null,
        val state: State? = null,
        val activeChannels: ActiveChannels? = null
) : Serializable {

    enum class Category {
        CARD_ACTIVITY,
        CARD_STATUS,
        LEGAL;

        fun toLocalizedString() =
            when (this) {
                CARD_ACTIVITY -> "notification_preferences.card_activity.title"
                CARD_STATUS -> "notification_preferences.card_status.title"
                LEGAL -> "notification_preferences.legal.title"
            }.localized()

    }

    enum class Group {
        PAYMENT_SUCCESSFUL,
        PAYMENT_DECLINED,
        ATM_WITHDRAWAL,
        INCOMING_TRANSFER,
        CARD_STATUS,
        LEGAL;

        fun toLocalizedString(): String {
            return when (this) {
                PAYMENT_SUCCESSFUL -> "notification_preferences.card_activity.payment_successful.title".localized()
                PAYMENT_DECLINED -> "notification_preferences.card_activity.payment_declined.title".localized()
                ATM_WITHDRAWAL -> "notification_preferences.card_activity.atm_withdrawal.title".localized()
                INCOMING_TRANSFER -> "notification_preferences.card_activity.incoming_transfer.title".localized()
                CARD_STATUS -> "notification_preferences.card_status.title".localized()
                LEGAL -> "notification_preferences.legal.title".localized()
            }
        }

        override fun toString(): String {
            return name.toLowerCase()
        }
    }

    enum class State {
        ENABLED,
        DISABLED
    }
}
