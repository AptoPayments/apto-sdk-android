package com.aptopayments.mobile.repository.paymentsources.remote.entities

import com.google.gson.annotations.SerializedName

internal data class PaymentSourcesListEntity(
    @SerializedName("payment_sources")
    val paymentSources: List<PaymentSourceWrapperEntity>?

) {
    fun toPaymentSourcesList() = paymentSources?.map { it.paymentSource.toPaymentSource() } ?: emptyList()
}
