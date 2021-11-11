package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.FeatureStatus
import com.aptopayments.mobile.data.card.GenericFeature
import com.google.gson.annotations.SerializedName

internal data class GenericFeatureEntity(
    @SerializedName("status")
    val status: String? = "",
) {
    fun toFeature() = GenericFeature(
        FeatureStatus.fromString(status ?: "").toBoolean(),
    )

    companion object {
        fun from(value: GenericFeature?): GenericFeatureEntity? {
            return value?.let {
                GenericFeatureEntity(
                    status = FeatureStatus.fromBoolean(value.isEnabled).toString()
                )
            }
        }
    }
}
