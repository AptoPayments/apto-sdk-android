package com.aptopayments.mobile.data.transfermoney

import com.aptopayments.mobile.data.card.Money
import com.aptopayments.mobile.data.payment.PaymentStatus
import org.threeten.bp.ZonedDateTime
import java.io.Serializable

data class P2pTransferResponse(
    val transferId: String,
    val status: PaymentStatus,
    val sourceId: String,
    val amount: Money,
    val recipientName: CardHolderName,
    val createdAt: ZonedDateTime,
) : Serializable
