package com.aptopayments.core.repository.transaction.remote

import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.platform.BaseService
import com.aptopayments.core.repository.transaction.TransactionListFilters

internal class TransactionService constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val transactionApi by lazy { apiCatalog.api().create(TransactionApi::class.java) }

    fun getTransactions(cardId: String, filters: TransactionListFilters) =
            transactionApi.getTransactions(
                    userToken = authorizationHeader(userSessionRepository.userToken),
                    cardId = cardId,
                    options = filters.toOptionsMap(),
                    state = filters.state
            )
}

fun TransactionListFilters.toOptionsMap(): Map<String, String?> {
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
