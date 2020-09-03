package com.aptopayments.mobile.data.card

import java.io.Serializable

data class FundingFeature(
    val isEnabled: Boolean,
    val cardNetworks: List<Card.CardNetwork>,
    val limits: FundingLimits,
    val softDescriptor: String
) : Serializable
