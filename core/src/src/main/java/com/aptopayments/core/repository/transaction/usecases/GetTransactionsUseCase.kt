package com.aptopayments.core.repository.transaction.usecases

import com.aptopayments.core.data.transaction.Transaction
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.transaction.TransactionListFilters
import com.aptopayments.core.repository.transaction.TransactionRepository

internal class GetTransactionsUseCase constructor(
    private val repository: TransactionRepository,
    networkHandler: NetworkHandler
) : UseCase<List<Transaction>, GetTransactionsUseCase.Params>(networkHandler) {

    override fun run(params: Params) =
        repository.getTransactions(params.cardId, params.filters, params.forceApiCall, params.clearCachedValues)

    data class Params(
        val cardId: String,
        val filters: TransactionListFilters,
        val forceApiCall: Boolean = true,
        val clearCachedValues: Boolean = false
    )
}
