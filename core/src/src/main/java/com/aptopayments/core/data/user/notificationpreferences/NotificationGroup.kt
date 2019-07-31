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

        fun toString(context: Context): String {
            context.let {
                return when (this) {
                    CARD_ACTIVITY -> "notification_preferences.card_activity.title".localized(it)
                    CARD_STATUS -> "notification_preferences.card_status.title".localized(it)
                    LEGAL -> "notification_preferences.legal.title".localized(it)
                }
            }
        }
    }

    enum class Group {
        PAYMENT_SUCCESSFUL,
        PAYMENT_DECLINED,
        ATM_WITHDRAWAL,
        INCOMING_TRANSFER,
        CARD_STATUS,
        LEGAL;

        fun toString(context: Context): String {
            context.let {
                return when (this) {
                    PAYMENT_SUCCESSFUL -> "notification_preferences.card_activity.payment_successful.title".localized(it)
                    PAYMENT_DECLINED -> "notification_preferences.card_activity.payment_declined.title".localized(it)
                    ATM_WITHDRAWAL -> "notification_preferences.card_activity.atm_withdrawal.title".localized(it)
                    INCOMING_TRANSFER -> "notification_preferences.card_activity.incoming_transfer.title".localized(it)
                    CARD_STATUS -> "notification_preferences.card_status.title".localized(it)
                    LEGAL -> "notification_preferences.legal.title".localized(it)
                }
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
