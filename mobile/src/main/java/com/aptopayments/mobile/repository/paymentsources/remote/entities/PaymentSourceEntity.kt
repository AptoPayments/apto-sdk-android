package com.aptopayments.mobile.repository.paymentsources.remote.entities

import com.aptopayments.mobile.data.paymentsources.PaymentSource

internal interface PaymentSourceEntity {
    val id: String
    val description: String?
    val isPreferred: Boolean

    fun toPaymentSource(): PaymentSource
}
