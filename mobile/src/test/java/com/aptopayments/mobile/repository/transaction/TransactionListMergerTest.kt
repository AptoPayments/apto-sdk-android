package com.aptopayments.mobile.repository.transaction

import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.data.transaction.Transaction
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

class TransactionListMergerTest {

    private val date00 = ZonedDateTime.of(2020, 11, 20, 16, 0, 0, 0, ZoneId.systemDefault())
    private val date01 = ZonedDateTime.of(2020, 11, 20, 16, 1, 0, 0, ZoneId.systemDefault())
    private val date02 = ZonedDateTime.of(2020, 11, 20, 16, 2, 0, 0, ZoneId.systemDefault())
    private val date03 = ZonedDateTime.of(2020, 11, 20, 16, 3, 0, 0, ZoneId.systemDefault())

    private val sut = TransactionListMerger()

    @Test
    fun `when both empty then empty`() {
        val result = sut.merge(emptyList(), emptyList())

        assertEquals(0, result.size)
    }

    @Test
    fun `when api list empty then then local`() {
        val apiList = emptyList<Transaction>()
        val localList = listOf(TestDataProvider.provideTransaction())

        val result = sut.merge(apiList, localList)

        assertEquals(localList, result)
    }

    @Test
    fun `when local list empty then then api`() {
        val apiList = listOf(TestDataProvider.provideTransaction())
        val localList = emptyList<Transaction>()

        val result = sut.merge(apiList, localList)

        assertEquals(apiList, result)
    }

    @Test
    fun `when local list is older than API then return both merged`() {
        val apiList = listOf(getTransactionWithDate(date03), getTransactionWithDate(date02))
        val localList = listOf(getTransactionWithDate(date01), getTransactionWithDate(date00))

        val result = sut.merge(apiList, localList)

        assertEquals(
            listOf(
                getTransactionWithDate(date03),
                getTransactionWithDate(date02),
                getTransactionWithDate(date01),
                getTransactionWithDate(date00)
            ),
            result
        )
    }

    @Test
    fun `when local list is contained in API list then return both merged`() {
        val apiList = listOf(getTransactionWithDate(date03), getTransactionWithDate(date02), getTransactionWithDate(date01))
        val localList = listOf(getTransactionWithDate(date01), getTransactionWithDate(date00))

        val result = sut.merge(apiList, localList)

        assertEquals(
            listOf(
                getTransactionWithDate(date03),
                getTransactionWithDate(date02),
                getTransactionWithDate(date01),
                getTransactionWithDate(date00)
            ),
            result
        )
    }

    private fun getTransactionWithDate(date: ZonedDateTime) =
        TestDataProvider.provideTransaction(createdAt = date)
}
