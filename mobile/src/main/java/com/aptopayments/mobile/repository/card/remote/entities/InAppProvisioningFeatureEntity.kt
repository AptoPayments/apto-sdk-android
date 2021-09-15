package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.FeatureStatus
import com.aptopayments.mobile.data.card.InAppProvisioningFeature
import com.google.gson.annotations.SerializedName

internal data class InAppProvisioningFeatureEntity(
    @SerializedName("status")
    val status: String? = "",
) {
    fun toFeature() = InAppProvisioningFeature(
        FeatureStatus.fromString(status ?: "").toBoolean(),
    )

    companion object {
        fun from(value: InAppProvisioningFeature?): InAppProvisioningFeatureEntity? {
            return value?.let {
                InAppProvisioningFeatureEntity(
                    status = FeatureStatus.fromBoolean(value.isEnabled).toString()
                )
            }
        }
    }
}
