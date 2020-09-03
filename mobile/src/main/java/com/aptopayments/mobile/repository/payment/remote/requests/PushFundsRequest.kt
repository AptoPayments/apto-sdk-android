package com.aptopayments.mobile.repository.payment.remote.requests

import com.aptopayments.mobile.repository.card.remote.entities.MoneyEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class PushFundsRequest(
    @SerializedName("amount")
    val amount: MoneyEntity,
    @SerializedName("balance_id")
    val balanceId: String
) : Serializable
