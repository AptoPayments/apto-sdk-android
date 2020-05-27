package com.aptopayments.core.repository.transaction.remote

import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.platform.BaseService
import com.aptopayments.core.repository.transaction.TransactionListFilters

internal class TransactionService constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val transactionApi by lazy { apiCatalog.api().create(TransactionApi::class.java) }

    fun getTransactions(cardId: String, filters: TransactionListFilters) =
        transactionApi.getTransactions(cardId = cardId, options = filters.toOptionsMap(), state = filters.state)
}
