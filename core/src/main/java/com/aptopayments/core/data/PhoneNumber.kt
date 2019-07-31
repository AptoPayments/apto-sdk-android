package com.aptopayments.core.data

import java.io.Serializable

data class PhoneNumber(
        val countryCode: String,
        val phoneNumber: String
) : Serializable {
    fun toStringRepresentation() = "+$countryCode $phoneNumber"
}
