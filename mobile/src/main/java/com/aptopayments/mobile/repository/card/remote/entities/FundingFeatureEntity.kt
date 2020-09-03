package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.data.card.FeatureStatus
import com.aptopayments.mobile.data.card.FundingFeature
import com.google.gson.annotations.SerializedName

internal data class FundingFeatureEntity(
    @SerializedName("status")
    val status: String = "",

    @SerializedName("card_networks")
    val cardNetworks: List<String> = listOf(),

    @SerializedName("limits")
    val limits: FundingLimitsEntity,

    @SerializedName("soft_descriptor")
    val softDescriptor: String?

) {
    fun toFundingFeature(): FundingFeature {
        return FundingFeature(
            isEnabled = FeatureStatus.ENABLED == FeatureStatus.fromString(status),
            cardNetworks = parseCardNetworksList(),
            limits = limits.toFundingLimits(),
            softDescriptor = softDescriptor ?: ""
        )
    }

    private fun parseCardNetworksList(): List<Card.CardNetwork> {
        return cardNetworks.map { Card.CardNetwork.fromString(it) }.filter { elem -> elem != Card.CardNetwork.UNKNOWN }
    }

    companion object {
        fun from(funding: FundingFeature?): FundingFeatureEntity? {
            return funding?.let {
                FundingFeatureEntity(
                    status = getStatusFromFunding(funding),
                    cardNetworks = funding.cardNetworks.map { it.toString() },
                    limits = FundingLimitsEntity.from(funding.limits),
                    softDescriptor = funding.softDescriptor
                )
            }
        }

        private fun getStatusFromFunding(funding: FundingFeature) =
            (if (funding.isEnabled) FeatureStatus.ENABLED else FeatureStatus.DISABLED).toString()
    }
}
