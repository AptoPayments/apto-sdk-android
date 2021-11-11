package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.FeatureStatus
import com.aptopayments.mobile.data.card.Ivr
import com.google.gson.annotations.SerializedName
import java.util.Locale

internal data class IvrEntity(

    @SerializedName("status")
    val status: String = "",

    @SerializedName("ivr_phone")
    val ivrPhoneEntity: PhoneNumberEntity? = null

) {
    fun toIvr() = Ivr(
        status = FeatureStatus.fromString(status),
        ivrPhone = ivrPhoneEntity?.toPhoneNumber()
    )

    companion object {
        fun from(getPin: Ivr?): IvrEntity? {
            return getPin?.let {
                IvrEntity(
                    status = it.status.toString().lowercase(Locale.US),
                    ivrPhoneEntity = PhoneNumberEntity.from(it.ivrPhone)
                )
            }
        }
    }
}
