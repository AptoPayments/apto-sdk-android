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
                CAPTURE -> "transaction_details_adjustment_capture_text"
                REFUND -> "transaction_details_adjustment_refund_text"
                HOLD -> "transaction_details_adjustment_hold_text"
                RELEASE -> "transaction_details_adjustment_release_text"
                OTHER -> "transaction_details_adjustment_other_text"
            }.localized()
    }
}
