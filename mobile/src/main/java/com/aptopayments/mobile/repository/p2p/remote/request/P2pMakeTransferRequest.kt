package com.aptopayments.mobile.repository.p2p.remote.request

import com.aptopayments.mobile.data.card.Money
import com.aptopayments.mobile.repository.card.remote.entities.MoneyEntity
import com.google.gson.annotations.SerializedName

internal data class P2pMakeTransferRequest(
    @SerializedName("source_id")
    val sourceId: String,
    @SerializedName("recipient_id")
    val recipientId: String,
    @SerializedName("amount")
    val amount: MoneyEntity,
) {
    companion object {
        fun create(sourceId: String, recipientId: String, amount: Money): P2pMakeTransferRequest {
            return P2pMakeTransferRequest(sourceId, recipientId, MoneyEntity.from(amount)!!)
        }
    }
}
