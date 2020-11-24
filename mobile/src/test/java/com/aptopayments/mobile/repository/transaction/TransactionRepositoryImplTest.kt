package com.aptopayments.mobile.repository.transaction

import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.data.transaction.Transaction
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.transaction.local.TransactionLocalDao
import com.aptopayments.mobile.repository.transaction.remote.TransactionService
import com.nhaarman.mockitokotlin2.*
import org.junit.Test

private const val CARD_ID = "card_id"
private val FILTERS = TransactionListFilters(rows = 20)

class TransactionRepositoryImplTest {

    private val service: TransactionService = mock()
    private val dao: TransactionLocalDao = mock()
    private val userSessionRepository: UserSessionRepository = mock()
    private val transactionListMerger = TransactionListMerger()

    private val sut = TransactionRepositoryImpl(service, dao, userSessionRepository, transactionListMerger)

    @Test
    fun `when forceApiCall then service gets called`() {
        val transactions = emptyList<Transaction>()
        configureServiceForReturning(transactions)

        val result = sut.getTransactions(CARD_ID, FILTERS, forceApiCall = true, clearCachedValues = false)

        result.shouldBeRightAndEqualTo(transactions)
        verify(service).getTransactions(CARD_ID, FILTERS)
    }

    @Test
    fun `when forceApiCall is false but empty db then service gets called`() {
        val transactions = emptyList<Transaction>()
        configureServiceForReturning(transactions)
        whenever(dao.getTransactions(CARD_ID)).thenReturn(emptyList())

        val result = sut.getTransactions(CARD_ID, FILTERS, forceApiCall = false, clearCachedValues = false)

        result.shouldBeRightAndEqualTo(transactions)
        verify(service).getTransactions(CARD_ID, FILTERS)
    }

    @Test
    fun `when forceApiCall is false but null db then service gets called`() {
        val transactions = emptyList<Transaction>()
        configureServiceForReturning(transactions)
        whenever(dao.getTransactions(CARD_ID)).thenReturn(emptyList())

        val result = sut.getTransactions(CARD_ID, FILTERS, forceApiCall = false, clearCachedValues = false)

        result.shouldBeRightAndEqualTo(transactions)
        verify(service).getTransactions(CARD_ID, FILTERS)
    }

    @Test
    fun `when forceApiCall is false and data in db then service doesn't get called`() {
        val transactions = emptyList<Transaction>()
        configureServiceForReturning(transactions)
        val localTransactions = listOf(TestDataProvider.provideTransactionLocalEntity())
        whenever(dao.getTransactions(CARD_ID)).thenReturn(localTransactions)

        val result = sut.getTransactions(CARD_ID, FILTERS, forceApiCall = false, clearCachedValues = false)

        result.shouldBeRightAndEqualTo(localTransactions.map { it.toTransaction() })
        verify(service, times(0)).getTransactions(CARD_ID, FILTERS)
    }

    private fun configureServiceForReturning(transactions: List<Transaction>) {
        whenever(service.getTransactions(any(), any())).thenReturn(transactions.right())
    }
}
