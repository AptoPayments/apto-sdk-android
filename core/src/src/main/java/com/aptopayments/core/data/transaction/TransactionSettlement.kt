package com.aptopayments.core.data.transaction

import com.aptopayments.core.data.card.Money
import org.threeten.bp.ZonedDateTime
import java.io.Serializable

data class TransactionSettlement(
    val createdAt: ZonedDateTime,
    val amount: Money?
) : Serializable
