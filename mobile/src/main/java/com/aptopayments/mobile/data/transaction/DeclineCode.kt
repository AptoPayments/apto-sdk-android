package com.aptopayments.mobile.data.transaction

import com.aptopayments.mobile.extension.localized
import java.util.Locale

enum class DeclineCode(val code: String) {
    DECLINE_NSF("decline_nsf"),
    DECLINE_BAD_PIN("decline_bad_pin"),
    DECLINE_06("decline_06"),
    DECLINE_42("decline_42"),
    DECLINE_60("decline_60"),
    DECLINE_61("decline_61"),
    DECLINE_65("decline_65"),
    DECLINE_70("decline_70"),
    DECLINE_75("decline_75"),
    DECLINE_BAD_CVV_OR_EXP("decline_bad_cvv_or_exp"),
    DECLINE_BAD_CVV("decline_bad_cvv"),
    DECLINE_PRE_ACTIVE("decline_pre_active"),
    DECLINE_MISC("decline_misc"),
    Other("");

    fun toLocalizedString(): String =
        if (this == Other) "transaction_details_details_decline_default".localized()
        else ("transaction_details_details_" + this.code).localized()

    companion object {
        fun from(code: String?): DeclineCode? = code?.let {
            try {
                valueOf(it.toUpperCase(Locale.US))
            } catch (exception: IllegalArgumentException) {
                Other
            }
        }
    }
}
