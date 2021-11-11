package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.FeatureStatus
import com.aptopayments.mobile.data.card.FeatureType
import com.aptopayments.mobile.data.card.GetPin
import com.google.gson.annotations.SerializedName
import java.util.Locale

internal data class GetPinEntity(

    @SerializedName("status")
    val status: String = "",

    @SerializedName("type")
    val type: String? = "",

    @SerializedName("ivr_phone")
    val ivrPhoneEntity: PhoneNumberEntity? = null

) {
    fun toGetPin() = GetPin(
        status = FeatureStatus.fromString(status),
        type = parseFeatureType(type)
    )

    private fun parseFeatureType(type: String?): FeatureType = when (type) {
        "ivr" -> FeatureType.Ivr(ivrPhoneEntity?.toPhoneNumber())
        "api" -> FeatureType.Api()
        "voip" -> FeatureType.Voip()
        else -> FeatureType.Unknown()
    }

    companion object {
        fun from(getPin: GetPin?): GetPinEntity? = getPin?.let { it ->
            var phone: PhoneNumberEntity? = null
            if (it.type is FeatureType.Ivr) {
                it.type.ivrPhone?.let { phoneNumber ->
                    phone = PhoneNumberEntity(
                        countryCode = phoneNumber.countryCode,
                        phoneNumber = phoneNumber.phoneNumber
                    )
                }
            }
            GetPinEntity(
                status = it.status.toString().lowercase(Locale.US),
                type = it.type.name,
                ivrPhoneEntity = phone
            )
        }
    }
}
