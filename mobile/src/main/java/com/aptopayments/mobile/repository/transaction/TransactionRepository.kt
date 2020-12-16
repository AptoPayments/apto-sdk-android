package com.aptopayments.mobile.repository.transaction

import com.aptopayments.mobile.data.transaction.Transaction
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.platform.BaseNoNetworkRepository
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.transaction.local.TransactionLocalDao
import com.aptopayments.mobile.repository.transaction.local.entities.TransactionLocalEntity
import com.aptopayments.mobile.repository.transaction.remote.TransactionService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal interface TransactionRepository : BaseNoNetworkRepository {

    fun getTransactions(
        cardId: String,
        filters: TransactionListFilters,
        forceApiCall: Boolean,
        clearCachedValues: Boolean
    ): Either<Failure, List<Transaction>>
}

internal class TransactionRepositoryImpl(
    private val service: TransactionService,
    private val transactionLocalDao: TransactionLocalDao,
    private val userSessionRepository: UserSessionRepository,
    private val transactionListMerger: TransactionListMerger
) : TransactionRepository {

    init {
        userSessionRepository.subscribeSessionInvalidListener(this) {
            GlobalScope.launch {
                transactionLocalDao.clearTransactionCache()
            }
        }
    }

    protected fun finalize() {
        userSessionRepository.unsubscribeSessionInvalidListener(this)
    }

    override fun getTransactions(
        cardId: String,
        filters: TransactionListFilters,
        forceApiCall: Boolean,
        clearCachedValues: Boolean
    ): Either<Failure, List<Transaction>> {
        return if (forceApiCall) {
            getTransactionsFromRemoteAPI(cardId, filters, clearCachedValues)
        } else {
            val localTransactions = transactionLocalDao.getTransactions(cardId)

            if (localTransactions.isNullOrEmpty()) {
                getTransactionsFromRemoteAPI(cardId, filters, clearCachedValues = true)
            } else {
                localTransactions.map { it.toTransaction() }.right()
            }
        }
    }

    private fun getTransactionsFromRemoteAPI(
        cardId: String,
        filters: TransactionListFilters,
        clearCachedValues: Boolean
    ): Either<Failure, List<Transaction>> {
        return service.getTransactions(cardId, filters).runIfRight { apiTransactionList ->
            if (apiTransactionList.isNotEmpty()) {
                if (filters.lastTransactionId != null) {
                    // Load more transactions - append them to cached transactions
                    saveTransactionsLocally(apiTransactionList, cardId)
                } else {
                    if (clearCachedValues) {
                        replaceCachedTransactions(apiTransactionList, cardId)
                    } else {
                        processBackgroundRefresh(apiTransactionList, cardId)
                    }
                }
            }
        }
    }

    private fun processBackgroundRefresh(apiTransactionList: List<Transaction>, cardId: String) {
        transactionLocalDao.getTransactions(cardId)?.let { transactions ->
            val localTransactions = transactions.map { it.toTransaction() }.toMutableList()
            if (localTransactions.isEmpty()) {
                saveTransactionsLocally(apiTransactionList, cardId)
            } else {
                val list = if (isAGapBetweenNewAndOldTransactions(apiTransactionList, localTransactions)) {
                    apiTransactionList
                } else {
                    transactionListMerger.merge(apiTransactionList, localTransactions)
                }
                replaceCachedTransactions(list, cardId)
            }
        }
    }

    private fun isAGapBetweenNewAndOldTransactions(
        apiTransactionList: List<Transaction>,
        currentTransactions: List<Transaction>
    ) = apiTransactionList.last().createdAt.isAfter(currentTransactions.first().createdAt)

    private fun replaceCachedTransactions(list: List<Transaction>, cardId: String) {
        transactionLocalDao.clearTransactionCache()
        saveTransactionsLocally(list, cardId)
    }

    private fun saveTransactionsLocally(list: List<Transaction>, cardId: String) {
        transactionLocalDao.saveTransactions(transformTransactions(list, cardId))
    }

    private fun transformTransactions(
        transactionList: List<Transaction>,
        cardId: String
    ) = transactionList.map { TransactionLocalEntity.fromTransaction(it, cardId) }
}
