package com.aptopayments.mobile.repository.transaction.remote.entities

import com.aptopayments.mobile.data.transaction.TransactionSettlement
import com.aptopayments.mobile.extension.toZonedDateTime
import com.aptopayments.mobile.repository.card.remote.entities.MoneyEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class TransactionSettlementEntity(

    @SerializedName("date")
    val createdAt: Double,

    @SerializedName("amount")
    val amount: MoneyEntity? = null

) : Serializable {

    fun toTransactionSettlement() = TransactionSettlement(
        createdAt = createdAt.toLong().toZonedDateTime(),
        amount = amount?.toMoney()
    )
}
