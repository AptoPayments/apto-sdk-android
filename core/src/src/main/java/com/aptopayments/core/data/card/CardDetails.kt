package com.aptopayments.core.data.card

import java.io.Serializable

data class CardDetails (
        val pan: String,
        val cvv: String,
        val expirationDate: String
) : Serializable
