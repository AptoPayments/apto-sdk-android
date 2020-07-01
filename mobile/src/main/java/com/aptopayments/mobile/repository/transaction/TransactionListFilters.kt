package com.aptopayments.mobile.repository.transaction

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
) {
    internal fun toOptionsMap(): Map<String, String?> {
        return hashMapOf(
            "page" to page?.toString(),
            "rows" to rows?.toString(),
            "last_transaction_id" to lastTransactionId,
            "start_date" to startDate?.toString(),
            "end_date" to endDate?.toString(),
            "mcc" to mccCode,
            "type" to type
        ).filter { it.value != null }.toMutableMap()
    }
}
