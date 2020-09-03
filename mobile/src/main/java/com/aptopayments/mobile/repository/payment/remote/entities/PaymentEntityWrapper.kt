package com.aptopayments.mobile.repository.payment.remote.entities

import com.google.gson.annotations.SerializedName

internal data class PaymentEntityWrapper(
    @SerializedName("payment")
    val payment: PaymentEntity
)
