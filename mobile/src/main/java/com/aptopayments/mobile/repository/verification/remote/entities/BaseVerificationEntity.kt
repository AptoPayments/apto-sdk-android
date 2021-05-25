package com.aptopayments.mobile.repository.verification.remote.entities

internal interface BaseVerificationEntity {
    val verificationType: String
    val verificationId: String
    val status: String
}
