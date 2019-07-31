package com.aptopayments.core.data.transaction

import com.aptopayments.core.data.card.Money
import java.io.Serializable
import java.util.*

data class TransactionSettlement (
        val createdAt: Date,
        val amount: Money?
) : Serializable
