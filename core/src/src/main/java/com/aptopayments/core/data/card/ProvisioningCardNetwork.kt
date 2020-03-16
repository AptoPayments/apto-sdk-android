package com.aptopayments.core.data.card

enum class ProvisioningCardNetwork {
    VISA, MASTERCARD, AMEX;

    companion object {
        fun fromString(str: String) = valueOf(str.toUpperCase())
    }
}
