package com.aptopayments.core.repository.transaction

import com.aptopayments.core.data.transaction.Transaction
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.platform.AptoPlatform
import com.aptopayments.core.repository.transaction.usecases.GetTransactionsUseCase

internal class FetchTransactionsTask(
        private val params: GetTransactionsUseCase.Params,
        private val onComplete: ((Either<Failure, List<Transaction>>) -> Unit)

) {
    var isExecuting = false
        private set
    var isFinished = false
        private set
    var isCancelled = false
        private set(cancelled) {
            field = cancelled
            if (cancelled) isExecuting = false
        }

    fun start() {
        if (isCancelled) return
        isExecuting = true
        run()
    }

    fun cancel() {
        isCancelled = true
    }

    private fun run() {
        if (isCancelled) return
        AptoPlatform.fetchCardTransactions(cardId = params.cardId, filters = params.filters,
                forceRefresh = params.forceApiCall, clearCachedValues = params.clearCachedValues) {
            if (isCancelled) return@fetchCardTransactions
            isExecuting = false
            isFinished = true
            onComplete(it)
        }
    }
}
