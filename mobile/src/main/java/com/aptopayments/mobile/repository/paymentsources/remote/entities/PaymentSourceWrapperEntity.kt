package com.aptopayments.mobile.repository.paymentsources.remote.entities

import com.google.gson.annotations.SerializedName

internal data class PaymentSourceWrapperEntity(
    @SerializedName("payment_source")
    val paymentSource: PaymentSourceEntity
)
