package com.aptopayments.mobile.data.payment

import com.aptopayments.mobile.extension.toUpperCaseDefault

enum class PaymentStatus(val legend: String) {
    PROCESSED("load_funds_transaction_status_completed"),
    PENDING("load_funds_transaction_status_pending"),
    FAILED("load_funds_transaction_status_error");

    companion object {
        fun fromString(string: String) =
            try {
                valueOf(string.toUpperCaseDefault())
            } catch (e: IllegalArgumentException) {
                FAILED
            }
    }
}
