package com.aptopayments.mobile.repository.transaction

import com.aptopayments.mobile.data.transaction.Transaction

class TransactionListMerger {

    fun merge(apiTransactionList: List<Transaction>, localTransactions: List<Transaction>): List<Transaction> {
        return when {
            localTransactions.isEmpty() -> apiTransactionList
            apiTransactionList.isEmpty() -> localTransactions
            else -> mergeLists(apiTransactionList, localTransactions)
        }
    }

    private fun mergeLists(apiTransactionList: List<Transaction>, localTransactions: List<Transaction>): List<Transaction> {
        val lastAPIDate = apiTransactionList.last().createdAt
        val cachedTransactionsFiltered = localTransactions.filter { it.createdAt.isBefore(lastAPIDate) }
        return apiTransactionList.plus(cachedTransactionsFiltered)
    }
}
