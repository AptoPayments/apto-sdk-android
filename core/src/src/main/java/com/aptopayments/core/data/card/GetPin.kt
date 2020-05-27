package com.aptopayments.core.data.card

import java.io.Serializable

data class GetPin(
    val status: FeatureStatus,
    val type: FeatureType
) : Serializable
