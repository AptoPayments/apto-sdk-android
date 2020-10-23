package com.aptopayments.mobile.data.paymentsources

import java.io.Serializable

/**
 * @property description String, Payment Source description. Optional parameter.
 */
interface NewPaymentSource : Serializable {
    val description: String?
}

/**
 * Data class that represents a new card that will be added
 *
 * @property description String, Card description. Optional parameter.
 * @property pan String, Card number without spaces.
 * @property cvv String, Card security number.
 * @property expirationMonth String, Two digits month.
 * @property expirationYear String, Four digits year (yyyy)
 * @property zipCode String, 5 digits zip code.
 */
data class NewCard(
    override val description: String?,
    val pan: String,
    val cvv: String,
    val expirationMonth: String,
    val expirationYear: String,
    val zipCode: String
) : NewPaymentSource
