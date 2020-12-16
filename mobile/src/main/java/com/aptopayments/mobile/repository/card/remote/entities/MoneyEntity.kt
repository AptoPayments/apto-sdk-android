package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.Money
import com.google.gson.annotations.SerializedName

internal data class MoneyEntity(

    @SerializedName("currency")
    val currency: String? = "",

    @SerializedName("amount")
    val amount: Double? = 0.0

) {
    fun toMoney() = Money(
        currency = currency,
        amount = amount
    )

    companion object {
        fun from(money: Money?): MoneyEntity? {
            return money?.let {
                MoneyEntity(currency = it.currency, amount = it.amount)
            }
        }
    }
}
