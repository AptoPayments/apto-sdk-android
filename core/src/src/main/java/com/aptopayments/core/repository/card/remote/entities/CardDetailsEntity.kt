package com.aptopayments.core.repository.card.remote.entities

import com.aptopayments.core.data.card.CardDetails
import com.google.gson.annotations.SerializedName

internal data class CardDetailsEntity(

        @SerializedName("pan")
        val pan: String = "",

        @SerializedName("cvv")
        val cvv: String = "",

        @SerializedName("expiration")
        val expirationDate: String = ""

) {
    fun toCardDetails() = CardDetails(
            pan = pan,
            cvv = cvv,
            expirationDate = expirationDate
    )
}
