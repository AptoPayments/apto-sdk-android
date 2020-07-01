package com.aptopayments.mobile.repository.transaction.remote

import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.platform.BaseService
import com.aptopayments.mobile.repository.transaction.TransactionListFilters

internal class TransactionService constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val transactionApi by lazy { apiCatalog.api().create(TransactionApi::class.java) }

    fun getTransactions(cardId: String, filters: TransactionListFilters) =
        transactionApi.getTransactions(cardId = cardId, options = filters.toOptionsMap(), state = filters.state)
}
