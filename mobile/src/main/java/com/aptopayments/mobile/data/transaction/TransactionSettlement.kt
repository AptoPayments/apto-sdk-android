package com.aptopayments.mobile.data.transaction

import com.aptopayments.mobile.data.card.Money
import org.threeten.bp.ZonedDateTime
import java.io.Serializable

data class TransactionSettlement(
    val createdAt: ZonedDateTime,
    val amount: Money?
) : Serializable
