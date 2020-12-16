package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.data.card.FeatureStatus
import com.aptopayments.mobile.data.card.FundingFeature
import com.aptopayments.mobile.data.card.FundingLimits
import com.google.gson.annotations.SerializedName

internal data class FundingFeatureEntity(
    @SerializedName("status")
    val status: String = "",

    @SerializedName("card_networks")
    val cardNetworks: List<String>? = listOf(),

    @SerializedName("limits")
    val limits: FundingLimitsEntity?,

    @SerializedName("soft_descriptor")
    val softDescriptor: String?

) {
    fun toFundingFeature(): FundingFeature {
        return FundingFeature(
            isEnabled = FeatureStatus.fromString(status).toBoolean(),
            cardNetworks = parseCardNetworksList(),
            limits = parseFundingLimits(),
            softDescriptor = softDescriptor ?: ""
        )
    }

    private fun parseCardNetworksList(): List<Card.CardNetwork> {
        return cardNetworks?.map { Card.CardNetwork.fromString(it) }
            ?.filter { elem -> elem != Card.CardNetwork.UNKNOWN }
            ?: emptyList()
    }

    private fun parseFundingLimits(): FundingLimits {
        return (limits ?: FundingLimitsEntity.getDefaultLimit()).toFundingLimits()
    }

    companion object {
        fun from(funding: FundingFeature?): FundingFeatureEntity? {
            return funding?.let {
                FundingFeatureEntity(
                    status = FeatureStatus.fromBoolean(funding.isEnabled).toString(),
                    cardNetworks = funding.cardNetworks.map { it.toString() },
                    limits = FundingLimitsEntity.from(funding.limits),
                    softDescriptor = funding.softDescriptor
                )
            }
        }
    }
}
