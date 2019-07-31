package com.aptopayments.core.data.user

import java.io.Serializable

enum class VerificationStatus {
    PENDING,
    PASSED,
    FAILED
}

data class Verification(
        var verificationId: String,
        var verificationType: String,
        var status : VerificationStatus = VerificationStatus.FAILED,
        var verificationDataPoint: String? = null,
        var secondaryCredential: Verification? = null,
        var secret: String? = null
) : Serializable
