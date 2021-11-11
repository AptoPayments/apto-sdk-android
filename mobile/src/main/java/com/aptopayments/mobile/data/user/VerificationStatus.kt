package com.aptopayments.mobile.data.user

import java.util.*

enum class VerificationStatus {
    PENDING,
    PASSED,
    FAILED;

    companion object {
        fun from(status: String): VerificationStatus {
            return try {
                valueOf(status.uppercase(Locale.US))
            } catch (exception: IllegalArgumentException) {
                FAILED
            }
        }
    }
}
