package com.aptopayments.core.data.transaction

import com.aptopayments.core.data.card.Money
import com.aptopayments.core.extension.localized
import java.io.Serializable
import java.util.*

data class TransactionAdjustment (
        val id: String?,
        val externalId: String?,
        val createdAt: Date?,
        val localAmount: Money?,
        val nativeAmount: Money?,
        val exchangeRate: Double?,
        val type: Type,
        val fundingSourceName: String?,
        val feeAmount: Money?
) : Serializable {

    enum class Type {
        CAPTURE,
        REFUND,
        HOLD,
        RELEASE,
        OTHER;

        override fun toString() =
            when (this) {
                CAPTURE -> "transaction_details.adjustment.capture.text"
                REFUND -> "transaction_details.adjustment.refund.text"
                HOLD -> "transaction_details.adjustment.hold.text"
                RELEASE -> "transaction_details.adjustment.release.text"
                OTHER -> "transaction_details.adjustment.other.text"
            }.localized()
    }
}
