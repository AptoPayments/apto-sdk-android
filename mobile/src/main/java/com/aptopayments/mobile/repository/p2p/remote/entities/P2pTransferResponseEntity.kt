package com.aptopayments.mobile.repository.p2p.remote.entities

import com.aptopayments.mobile.data.payment.PaymentStatus
import com.aptopayments.mobile.data.transfermoney.P2pTransferResponse
import com.aptopayments.mobile.repository.card.remote.entities.MoneyEntity
import com.google.gson.annotations.SerializedName
import org.threeten.bp.ZonedDateTime

internal data class P2pTransferResponseEntity(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("status")
    val status: String,
    @SerializedName("source_id")
    val sourceId: String? = "",
    @SerializedName("amount")
    val amount: MoneyEntity,
    @SerializedName("recipient")
    val recipient: CardholderDataEntity,
    @SerializedName("created_at")
    val createdAt: String
) {
    fun toModelObject(): P2pTransferResponse {
        return P2pTransferResponse(
            transferId = id ?: "",
            status = PaymentStatus.fromString(status),
            sourceId = sourceId ?: "",
            amount = amount.toMoney(),
            recipientName = recipient.name.toModelObject(),
            createdAt = ZonedDateTime.parse(createdAt),
        )
    }
}
