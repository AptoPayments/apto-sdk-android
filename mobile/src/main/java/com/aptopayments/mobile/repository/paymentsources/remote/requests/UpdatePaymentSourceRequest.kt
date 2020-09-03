package com.aptopayments.mobile.repository.paymentsources.remote.requests

import com.google.gson.annotations.SerializedName

internal data class UpdatePaymentSourceRequest(
    @SerializedName("description")
    val description: String,
    @SerializedName("is_preferred")
    val isPreferred: Boolean
)
