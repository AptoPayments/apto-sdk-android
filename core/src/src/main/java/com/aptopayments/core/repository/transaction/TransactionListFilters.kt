package com.aptopayments.core.repository.transaction

import org.threeten.bp.LocalDate

data class TransactionListFilters(
        val page: Int? = null,
        val rows: Int?,
        val lastTransactionId: String? = null,
        val startDate: LocalDate? = null,
        val endDate: LocalDate? = null,
        val mccCode: String? = null,
        val type: String? = null,
        val state: List<String> = listOf("complete")
)
