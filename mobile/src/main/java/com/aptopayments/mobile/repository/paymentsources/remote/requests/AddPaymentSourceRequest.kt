package com.aptopayments.mobile.repository.paymentsources.remote.requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class AddPaymentSourceRequest(
    @SerializedName("type")
    val type: String,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("card")
    val card: CardRequest? = null,
    @SerializedName("bank")
    val bank: BankAccountRequest? = null
) : Serializable

internal data class CardRequest(
    @SerializedName("pan")
    private val pan: String = "",
    @SerializedName("cvv")
    private val cvv: String = "",
    @SerializedName("exp_date")
    private val expiration: String = "",
    @SerializedName("last_four")
    val lastFour: String,
    @SerializedName("postal_code")
    val postalCode: String
) : Serializable

internal data class BankAccountRequest(
    @SerializedName("routing_number")
    private val routingNumber: String = "",
    @SerializedName("account_number")
    private val accountNumber: String = ""
) : Serializable

internal enum class AddPaymentSourceType {
    CARD, BANK
}
