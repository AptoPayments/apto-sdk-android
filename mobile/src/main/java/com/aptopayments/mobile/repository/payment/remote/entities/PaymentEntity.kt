package com.aptopayments.mobile.repository.payment.remote.entities

import com.aptopayments.mobile.data.payment.Payment
import com.aptopayments.mobile.data.payment.PaymentStatus
import com.aptopayments.mobile.repository.card.remote.entities.MoneyEntity
import com.aptopayments.mobile.repository.paymentsources.remote.entities.PaymentSourceEntity
import com.google.gson.annotations.SerializedName
import org.threeten.bp.ZonedDateTime

internal data class PaymentEntity(
    @SerializedName("id")
    val id: String?,

    @SerializedName("status")
    val status: String,

    @SerializedName("amount")
    val amount: MoneyEntity,

    @SerializedName("source")
    val source: PaymentSourceEntity,

    @SerializedName("approval_code")
    val approvalCode: String?,

    @SerializedName("created_at")
    val createdAt: String
) {
    fun toPayment(): Payment {
        return Payment(
            id = id ?: "",
            status = PaymentStatus.fromString(status),
            amount = amount.toMoney(),
            source = source.toPaymentSource(),
            approvalCode = approvalCode ?: "",
            createdAt = ZonedDateTime.parse(createdAt)
        )
    }
}
