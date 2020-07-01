package com.aptopayments.mobile.data.card

import java.util.Locale

enum class ProvisioningCardNetwork {
    VISA, MASTERCARD, AMEX;

    companion object {
        fun fromString(str: String) = valueOf(str.toUpperCase(Locale.US))
    }
}
