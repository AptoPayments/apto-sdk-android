package com.aptopayments.core.repository.card.remote.entities

import com.aptopayments.core.data.card.Activation
import com.google.gson.annotations.SerializedName

internal data class ActivationEntity(

    @SerializedName("ivr_phone")
    val ivrPhoneEntity: PhoneNumberEntity? = null

) {
    fun toActivation() = Activation(ivrPhone = ivrPhoneEntity?.toPhoneNumber())

    companion object {
        fun from(activation: Activation?): ActivationEntity? {
            return ActivationEntity(ivrPhoneEntity = PhoneNumberEntity.from(activation?.ivrPhone))
        }
    }
}
