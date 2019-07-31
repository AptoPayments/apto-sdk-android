package com.aptopayments.core.repository.card.remote.entities

import com.aptopayments.core.data.card.FeatureStatus
import com.aptopayments.core.data.card.Ivr
import com.google.gson.annotations.SerializedName

internal data class IvrEntity(

        @SerializedName("status")
        val status: String = "",

        @SerializedName("ivr_phone")
        val ivrPhoneEntity: PhoneNumberEntity? = null

) {
    fun toIvr() = Ivr(
            status = parseFeatureStatus(status),
            ivrPhone = ivrPhoneEntity?.toPhoneNumber()
    )

    private fun parseFeatureStatus(state: String): FeatureStatus {
        return try {
            FeatureStatus.valueOf(state.toUpperCase())
        } catch (exception: Throwable) {
            FeatureStatus.DISABLED
        }
    }

    companion object {
        fun from(getPin: Ivr?): IvrEntity? {
            return getPin?.let {
                IvrEntity(
                        status = it.status.toString().toLowerCase(),
                        ivrPhoneEntity = PhoneNumberEntity.from(it.ivrPhone)
                )
            }
        }
    }
}
