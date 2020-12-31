package com.aptopayments.mobile.data.card

import java.io.Serializable

data class CardPasscodeFeature(
    val isEnabled: Boolean = false,
    val isPasscodeSet: Boolean = false,
    val isVerificationRequired: Boolean = false,
) : Serializable
