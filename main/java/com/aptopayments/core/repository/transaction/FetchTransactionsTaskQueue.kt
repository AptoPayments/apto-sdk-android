package com.aptopayments.core.repository.transaction

import com.aptopayments.core.data.transaction.Transaction
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.platform.AptoPlatformProtocol
import com.aptopayments.core.repository.transaction.usecases.GetTransactionsUseCase

class FetchTransactionsTaskQueue constructor(private val aptoPlatformProtocol: AptoPlatformProtocol) {
    private var loadOperation: FetchTransactionsTask? = null
    private var loadMoreOperation: FetchTransactionsTask? = null
    private var backgroundRefreshOperation: FetchTransactionsTask? = null
    private val stateList: ArrayList<String>
        get() {
            val stateList = arrayListOf("complete")
            if (aptoPlatformProtocol.isShowDetailedCardActivityEnabled()) {
                stateList.add("declined")
            }
            return stateList
        }

    private fun isLoadInProgress(): Boolean = loadOperation?.isExecuting ?: false
    private fun isLoadMoreInProgress(): Boolean = loadMoreOperation?.isExecuting ?: false
    private fun isBackgroundRefreshInProgress(): Boolean = backgroundRefreshOperation?.isExecuting ?: false

    fun loadTransactions(cardID: String, rows: Int = 20, forceApiCall: Boolean,
                         clearCachedValue: Boolean, onComplete: ((Either<Failure, List<Transaction>>) -> Unit)) {
        if (isLoadInProgress()) return
        loadMoreOperation?.cancel()
        val params = GetTransactionsUseCase.Params(
                cardId = cardID,
                filters = TransactionListFilters(rows = rows, state = stateList),
                forceApiCall = forceApiCall,
                clearCachedValues = clearCachedValue
        )
        val task = FetchTransactionsTask(params, onComplete)
        loadOperation = task
        task.start()
    }

    fun loadMoreTransactions(cardID: String, lastTransactionId: String?, rows: Int = 20,
                             onComplete: ((Either<Failure, List<Transaction>>) -> Unit)) {
        if (isLoadInProgress() || isLoadMoreInProgress()) return
        val params = GetTransactionsUseCase.Params(
                cardId = cardID,
                filters = TransactionListFilters(
                        rows = rows,
                        lastTransactionId = lastTransactionId,
                        state = stateList),
                forceApiCall = true,
                clearCachedValues = false
        )
        val task = FetchTransactionsTask(params, onComplete)
        loadMoreOperation = task
        task.start()
    }

    fun backgroundRefresh(cardID: String, rows: Int = 20, onComplete: ((Either<Failure, List<Transaction>>) -> Unit)) {
        if (isLoadInProgress() || isLoadMoreInProgress() || isBackgroundRefreshInProgress()) return
        val params = GetTransactionsUseCase.Params(
                cardId = cardID,
                filters = TransactionListFilters(rows = rows, state = stateList),
                forceApiCall = true,
                clearCachedValues = false
        )
        val task = FetchTransactionsTask(params, onComplete)
        backgroundRefreshOperation = task
        task.start()
    }
}
