package com.aptopayments.mobile.data.transaction

import com.aptopayments.mobile.data.card.Money
import com.aptopayments.mobile.extension.localized
import org.threeten.bp.ZonedDateTime
import java.io.Serializable

data class TransactionAdjustment(
    val id: String?,
    val externalId: String?,
    val createdAt: ZonedDateTime?,
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

        fun toLocalizedDescription() =
            when (this) {
                CAPTURE -> "transaction_details.adjustment.capture.text"
                REFUND -> "transaction_details.adjustment.refund.text"
                HOLD -> "transaction_details.adjustment.hold.text"
                RELEASE -> "transaction_details.adjustment.release.text"
                OTHER -> "transaction_details.adjustment.other.text"
            }.localized()
    }
}
