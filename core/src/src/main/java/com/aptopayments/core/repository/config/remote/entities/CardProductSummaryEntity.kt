package com.aptopayments.core.repository.config.remote.entities

import com.aptopayments.core.data.cardproduct.CardProductSummary
import com.google.gson.annotations.SerializedName

internal data class CardProductSummaryEntity(

    @SerializedName("id")
    val id: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("allowed_countries")
    val countries: List<String>? = null
) {
    fun toCardProductSummary() = CardProductSummary(
        id = id,
        name = name,
        countries = countries
    )
}
