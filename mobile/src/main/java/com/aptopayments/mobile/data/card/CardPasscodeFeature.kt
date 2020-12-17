package com.aptopayments.mobile.data.card

data class CardPasscodeFeature(
    val isEnabled: Boolean = false,
    val isPasscodeSet: Boolean = false,
    val isVerificationRequired: Boolean = false,
)
