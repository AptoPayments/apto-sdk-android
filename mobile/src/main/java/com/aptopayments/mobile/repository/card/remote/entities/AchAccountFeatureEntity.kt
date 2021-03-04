package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.AchAccountFeature
import com.aptopayments.mobile.data.card.FeatureStatus
import com.aptopayments.mobile.repository.fundingsources.remote.entities.AchAccountDetailsEntity
import com.google.gson.annotations.SerializedName

internal data class AchAccountFeatureEntity(
    @SerializedName("status")
    val status: String? = "",

    @SerializedName("account_provisioned")
    val isAccountProvisioned: Boolean? = false,

    @SerializedName("disclaimer")
    val disclaimer: DisclaimerEntity? = null,

    @SerializedName("account_details")
    val accountDetails: AchAccountDetailsEntity? = null,
) {
    fun toAchFeature() = AchAccountFeature(
        FeatureStatus.fromString(status ?: "").toBoolean(),
        isAccountProvisioned = isAccountProvisioned ?: false,
        disclaimer = disclaimer?.toDisclaimer(),
        accountDetails = accountDetails?.toAchAccountDetails()
    )

    companion object {
        fun from(value: AchAccountFeature?): AchAccountFeatureEntity? {
            return value?.let {
                AchAccountFeatureEntity(
                    status = FeatureStatus.fromBoolean(value.isEnabled).toString(),
                    isAccountProvisioned = value.isAccountProvisioned,
                    disclaimer = value.disclaimer?.let { DisclaimerEntity.from(it) },
                    accountDetails = AchAccountDetailsEntity.fromAchAccountDetails(value.accountDetails),
                )
            }
        }
    }
}
