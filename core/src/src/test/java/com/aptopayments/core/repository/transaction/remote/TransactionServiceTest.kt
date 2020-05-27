package com.aptopayments.core.repository.transaction.remote

import com.aptopayments.core.ServiceTest
import com.aptopayments.core.data.TestDataProvider
import com.aptopayments.core.network.ListEntity
import com.aptopayments.core.repository.transaction.TransactionListFilters
import com.aptopayments.core.repository.transaction.remote.entities.TransactionEntity
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.test.inject
import java.lang.reflect.Type
import java.net.HttpURLConnection.HTTP_OK

internal class TransactionServiceTest : ServiceTest() {

    private val sut: TransactionService by inject()
    private val filters = TransactionListFilters(rows = 20, state = listOf("complete"))
    private val cardId = TestDataProvider.provideCardId()

    @Test
    fun `when getTransactions then request is made to the correct url`() {
        enqueueFile("userAccountsTransactionsResponse200.json")

        val response = executeGetTransactions()

        assertRequestSentTo("v1/user/accounts/$cardId/transactions?rows=20&state=complete", "GET")
        assertCode(response, HTTP_OK)
    }

    @Test
    fun `when getTransactions then response parses correctly`() {
        val fileContent = readFile("userAccountsTransactionsResponse200.json")
        val listType: Type = object : TypeToken<ListEntity<TransactionEntity>?>() {}.type
        val entity: ListEntity<TransactionEntity> = gson.fromJson(fileContent, listType)
        enqueueContent(fileContent)

        val response = executeGetTransactions()

        assertEquals(entity, response.body()!!)
    }

    private fun executeGetTransactions() = sut.getTransactions(cardId, filters).execute()
}
