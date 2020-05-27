package com.aptopayments.core.data.cardproduct

import java.io.Serializable

data class CardProductSummary(
    val id: String = "",
    val name: String = "",
    val countries: List<String>? = null
) : Serializable
