package com.aptopayments.mobile.data.paymentsources

import com.aptopayments.mobile.data.card.Card
import java.io.Serializable

interface PaymentSource : Serializable {
    val id: String
    val description: String?
    val isPreferred: Boolean
}

data class Card(
    override val id: String,
    override val description: String?,
    override val isPreferred: Boolean,
    val network: Card.CardNetwork,
    val lastFour: String
) : PaymentSource

data class BankAccount(
    override val id: String,
    override val description: String?,
    override val isPreferred: Boolean
) : PaymentSource
