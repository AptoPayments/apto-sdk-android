package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.PhoneNumber
import com.google.gson.annotations.SerializedName

internal data class PhoneNumberEntity(

    @SerializedName("phone_number")
    val phoneNumber: String? = "",

    @SerializedName("country_code")
    val countryCode: String? = ""

) {
    fun toPhoneNumber() = PhoneNumber(
        phoneNumber = phoneNumber ?: "",
        countryCode = countryCode ?: ""
    )

    companion object {
        fun from(phoneNumber: PhoneNumber?): PhoneNumberEntity? {
            return phoneNumber?.let {
                return PhoneNumberEntity(
                    phoneNumber = it.phoneNumber,
                    countryCode = it.countryCode
                )
            }
        }
    }
}
