package com.aptopayments.core.repository.transaction.remote.entities

import com.aptopayments.core.data.transaction.TransactionSettlement
import com.aptopayments.core.extension.toZonedDateTime
import com.aptopayments.core.repository.card.remote.entities.MoneyEntity
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
