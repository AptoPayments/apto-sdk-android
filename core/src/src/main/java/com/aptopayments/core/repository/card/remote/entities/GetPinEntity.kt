package com.aptopayments.core.repository.card.remote.entities

import com.aptopayments.core.data.card.FeatureStatus
import com.aptopayments.core.data.card.FeatureType
import com.aptopayments.core.data.card.GetPin
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
        status = parseFeatureStatus(status),
        type = parseFeatureType(type)
    )

    private fun parseFeatureStatus(state: String): FeatureStatus {
        return try {
            FeatureStatus.valueOf(state.toUpperCase(Locale.US))
        } catch (exception: Throwable) {
            FeatureStatus.DISABLED
        }
    }

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
                status = it.status.toString().toLowerCase(Locale.US),
                type = it.type.name,
                ivrPhoneEntity = phone
            )
        }
    }
}
