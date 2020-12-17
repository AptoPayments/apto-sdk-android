package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.CardPasscodeFeature
import com.aptopayments.mobile.data.card.FeatureStatus
import com.google.gson.annotations.SerializedName

internal data class CardPasscodeFeatureEntity(
    @SerializedName("status")
    val status: String? = "",

    @SerializedName("passcode_set")
    val passcodeSet: Boolean? = false,

    @SerializedName("verification_required")
    val verificationRequired: Boolean? = true,
) {
    fun toCardPasscode() = CardPasscodeFeature(
        calculateEnabled(),
        passcodeSet ?: false,
        isVerificationRequired = verificationRequired ?: true
    )

    private fun calculateEnabled() = FeatureStatus.fromString(status ?: "").toBoolean()

    companion object {
        fun from(value: CardPasscodeFeature?): CardPasscodeFeatureEntity? {
            return value?.let {
                CardPasscodeFeatureEntity(
                    status = FeatureStatus.fromBoolean(value.isEnabled).toString(),
                    passcodeSet = value.isEnabled,
                    verificationRequired = value.isVerificationRequired
                )
            }
        }
    }
}
