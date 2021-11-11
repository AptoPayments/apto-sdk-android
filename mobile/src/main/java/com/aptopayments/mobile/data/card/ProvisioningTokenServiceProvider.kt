package com.aptopayments.mobile.data.card

import java.util.Locale

enum class ProvisioningTokenServiceProvider {
    VISA, MASTERCARD, AMEX;

    companion object {
        fun fromString(str: String) = valueOf(str.uppercase(Locale.US))
    }
}
