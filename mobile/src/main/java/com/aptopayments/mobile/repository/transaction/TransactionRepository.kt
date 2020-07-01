package com.aptopayments.mobile.repository.transaction

import com.aptopayments.mobile.data.transaction.Transaction
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.platform.BaseRepository
import com.aptopayments.mobile.repository.transaction.local.TransactionLocalDao
import com.aptopayments.mobile.repository.transaction.local.entities.TransactionLocalEntity
import com.aptopayments.mobile.repository.transaction.remote.TransactionService
import com.aptopayments.mobile.repository.transaction.remote.entities.TransactionEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal interface TransactionRepository : BaseRepository {

    fun getTransactions(
        cardId: String,
        filters: TransactionListFilters,
        forceApiCall: Boolean,
        clearCachedValues: Boolean
    ): Either<Failure, List<Transaction>>

    class Network constructor(
        private val networkHandler: NetworkHandler,
        private val service: TransactionService,
        private val transactionLocalDao: TransactionLocalDao
    ) : BaseRepository.BaseRepositoryImpl(), TransactionRepository {

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
            if (forceApiCall) return getTransactionsFromRemoteAPI(cardId, filters, clearCachedValues)

            transactionLocalDao.getTransactions(cardId)?.let { localTransactions ->
                if (localTransactions.isEmpty()) return getTransactionsFromRemoteAPI(
                    cardId,
                    filters,
                    clearCachedValues = true
                )
                return Either.Right(localTransactions.map { it.toTransaction() })
            } ?: return getTransactionsFromRemoteAPI(cardId, filters, clearCachedValues = true)
        }

        private fun getTransactionsFromRemoteAPI(
            cardId: String,
            filters: TransactionListFilters,
            clearCachedValues: Boolean
        ): Either<Failure, List<Transaction>> {
            return when (networkHandler.isConnected) {
                true -> {
                    request(service.getTransactions(cardId, filters), { listEntity: ListEntity<TransactionEntity> ->
                        val transactionList = listEntity.data?.map {
                            it.toTransaction()
                        } ?: emptyList()

                        if (transactionList.isEmpty()) return@request transactionList

                        if (filters.lastTransactionId != null) {
                            // Load more transactions - append them to cached transactions
                            transactionLocalDao.saveTransactions(transactionList.map {
                                TransactionLocalEntity.fromTransaction(it, cardId)
                            })
                        } else {
                            if (clearCachedValues) {
                                // Pull to refresh - clear cache and save new ones
                                replaceCachedTransactionsWith(transactionList.map {
                                    TransactionLocalEntity.fromTransaction(it, cardId)
                                })
                            } else {
                                // Background refresh - prepend distinct transactions to cached transactions
                                transactionLocalDao.getTransactions(cardId)?.let { localTransactions ->
                                    val currentTransactions =
                                        localTransactions.map { it.toTransaction() } as ArrayList<Transaction>
                                    if (currentTransactions.isEmpty()) {
                                        replaceCachedTransactionsWith(transactionList.map {
                                            TransactionLocalEntity.fromTransaction(it, cardId)
                                        })
                                    }

                                    if (transactionList.last().createdAt.isAfter(currentTransactions.first().createdAt)) {
                                        // There's a gap between new and old transactions. Instead of paginating, we just store the new ones
                                        replaceCachedTransactionsWith(transactionList.map {
                                            TransactionLocalEntity.fromTransaction(it, cardId)
                                        })
                                    } else {
                                        var newTransactionIndex = 0
                                        val topCachedTransactionDate = currentTransactions.first().createdAt
                                        while (newTransactionIndex < transactionList.size && transactionList[newTransactionIndex].createdAt.isAfter(
                                                topCachedTransactionDate
                                            )
                                        ) {
                                            currentTransactions.add(0, transactionList[newTransactionIndex])
                                            newTransactionIndex++
                                        }
                                        replaceCachedTransactionsWith(currentTransactions.map {
                                            TransactionLocalEntity.fromTransaction(it, cardId)
                                        })
                                    }
                                }
                            }
                        }
                        transactionList
                    }, ListEntity())
                }
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        private fun replaceCachedTransactionsWith(newTransactions: List<TransactionLocalEntity>) {
            transactionLocalDao.clearTransactionCache()
            transactionLocalDao.saveTransactions(newTransactions)
        }
    }
}
