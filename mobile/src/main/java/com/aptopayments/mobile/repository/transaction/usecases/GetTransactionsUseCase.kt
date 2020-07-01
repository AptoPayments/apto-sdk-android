package com.aptopayments.mobile.repository.transaction.usecases

import com.aptopayments.mobile.data.transaction.Transaction
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.transaction.TransactionListFilters
import com.aptopayments.mobile.repository.transaction.TransactionRepository

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
