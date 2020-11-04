package com.aptopayments.mobile.data.payment

import com.aptopayments.mobile.data.card.Money
import com.aptopayments.mobile.data.paymentsources.PaymentSource
import org.threeten.bp.ZonedDateTime
import java.io.Serializable

/**
 * Represent a payment made to fund the account
 *
 * @property id internal id of a payment.
 * @property status [PaymentStatus] of a payment.
 * @property amount [Money] that represents the currency and amount.
 * @property source [PaymentSource] that represents the origin of the funds.
 * @property approvalCode Transaction's approval code.
 * @property createdAt [ZonedDateTime] Creation date of the Payment.

 */
data class Payment(
    val id: String,
    val status: PaymentStatus,
    val amount: Money,
    val source: PaymentSource,
    val approvalCode: String,
    val createdAt: ZonedDateTime
) : Serializable
