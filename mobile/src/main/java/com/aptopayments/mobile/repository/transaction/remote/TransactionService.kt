package com.aptopayments.mobile.repository.transaction.remote

import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.platform.BaseNetworkService
import com.aptopayments.mobile.repository.transaction.TransactionListFilters
import com.aptopayments.mobile.repository.transaction.remote.entities.TransactionEntity

internal class TransactionService(apiCatalog: ApiCatalog) : BaseNetworkService() {

    private val transactionApi by lazy { apiCatalog.api().create(TransactionApi::class.java) }

    fun getTransactions(cardId: String, filters: TransactionListFilters) =
        request(
            transactionApi.getTransactions(
                cardId = cardId,
                options = filters.toOptionsMap(),
                state = filters.state
            ),
            { listEntity: ListEntity<TransactionEntity> ->
                listEntity.data?.map {
                    it.toTransaction()
                } ?: emptyList()
            },
            ListEntity()
        )
}
