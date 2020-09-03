package com.aptopayments.mobile.repository.paymentsources.remote.entities

import com.aptopayments.mobile.data.paymentsources.Card
import com.google.gson.annotations.SerializedName

internal data class CardEntity(
    @SerializedName("id")
    override val id: String,
    @SerializedName("description")
    override val description: String? = null,
    @SerializedName("is_preferred")
    override val isPreferred: Boolean = false,
    @SerializedName("network")
    private val network: String? = "",
    @SerializedName("last_four")
    private val lastFour: String = ""
) : PaymentSourceEntity {

    override fun toPaymentSource() = Card(
        id = id,
        description = description,
        isPreferred = isPreferred,
        network = com.aptopayments.mobile.data.card.Card.CardNetwork.fromString(network ?: ""),
        lastFour = lastFour
    )
}
