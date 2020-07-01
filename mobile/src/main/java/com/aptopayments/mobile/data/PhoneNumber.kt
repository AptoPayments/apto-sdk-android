package com.aptopayments.mobile.data

import java.io.Serializable

/**
 * Data class to hold a phone number
 *
 * @property countryCode international numeric country code
 * @property phoneNumber phone number containing only numbers
 */
data class PhoneNumber(val countryCode: String, val phoneNumber: String) : Serializable {

    fun toStringRepresentation() = "+$countryCode $phoneNumber"
}
