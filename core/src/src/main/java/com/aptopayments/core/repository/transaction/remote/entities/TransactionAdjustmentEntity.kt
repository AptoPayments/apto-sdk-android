package com.aptopayments.core.repository.transaction.remote.entities

import com.aptopayments.core.data.transaction.TransactionAdjustment
import com.aptopayments.core.extension.toZonedDateTime
import com.aptopayments.core.repository.card.remote.entities.MoneyEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Locale

internal data class TransactionAdjustmentEntity(

    @SerializedName("id")
    val id: String?,

    @SerializedName("external_id")
    val externalId: String?,

    @SerializedName("created_at")
    val createdAt: Double?,

    @SerializedName("local_amount")
    val localAmount: MoneyEntity?,

    @SerializedName("native_amount")
    val nativeAmount: MoneyEntity?,

    @SerializedName("exchange_rate")
    val exchangeRate: Double?,

    @SerializedName("adjustment_type")
    val type: String?,

    @SerializedName("funding_source_name")
    val fundingSourceName: String?,

    @SerializedName("fee")
    val feeAmount: MoneyEntity?

) : Serializable {

    fun toTransactionAdjustment() = TransactionAdjustment(
        id = id,
        externalId = externalId,
        createdAt = createdAt?.toLong()?.toZonedDateTime(),
        localAmount = localAmount?.toMoney(),
        nativeAmount = nativeAmount?.toMoney(),
        exchangeRate = exchangeRate,
        type = parseTransactionAdjustmentType(type),
        fundingSourceName = fundingSourceName,
        feeAmount = feeAmount?.toMoney()
    )

    private fun parseTransactionAdjustmentType(type: String?): TransactionAdjustment.Type {
        return type?.let {
            try {
                TransactionAdjustment.Type.valueOf(it.toUpperCase(Locale.US))
            } catch (exception: Throwable) {
                TransactionAdjustment.Type.OTHER
            }
        } ?: TransactionAdjustment.Type.OTHER
    }
}
