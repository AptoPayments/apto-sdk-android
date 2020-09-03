package com.aptopayments.mobile.data.paymentsources

import java.io.Serializable

interface NewPaymentSource : Serializable {
    val description: String?
}

data class NewCard(
    override val description: String?,
    val pan: String,
    val cvv: String,
    val expirationMonth: String,
    val expirationYear: String,
    val zipCode: String
) : NewPaymentSource

data class NewBankAccount(
    override val description: String?,
    val routingNumber: String,
    val accountNumber: String
) : NewPaymentSource
