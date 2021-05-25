package com.aptopayments.mobile.data.user

import java.io.Serializable

data class Verification(
    val verificationId: String,
    val verificationType: String,
    val status: VerificationStatus = VerificationStatus.FAILED,
    var verificationDataPoint: String? = null,
    val secondaryCredential: Verification? = null,
    var secret: String? = null
) : Serializable
