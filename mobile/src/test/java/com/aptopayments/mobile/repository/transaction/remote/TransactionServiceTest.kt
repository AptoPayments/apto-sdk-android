package com.aptopayments.mobile.repository.transaction.remote

import com.aptopayments.mobile.NetworkServiceTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.repository.transaction.TransactionListFilters
import com.aptopayments.mobile.repository.transaction.remote.entities.TransactionEntity
import com.google.gson.reflect.TypeToken
import org.junit.Test
import org.koin.test.inject
import java.lang.reflect.Type

internal class TransactionServiceTest : NetworkServiceTest() {

    private val sut: TransactionService by inject()
    private val filters = TransactionListFilters(rows = 20, state = listOf("complete"))
    private val cardId = TestDataProvider.provideCardId()

    @Test
    fun `when getTransactions then request is made to the correct url`() {
        enqueueFile("userAccountsTransactionsResponse200.json")

        sut.getTransactions(cardId, filters)

        assertRequestSentTo("v1/user/accounts/$cardId/transactions?rows=20&state=complete", "GET")
    }

    @Test
    fun `when getTransactions then response parses correctly`() {
        val fileContent = readFile("userAccountsTransactionsResponse200.json")
        val listType: Type = object : TypeToken<ListEntity<TransactionEntity>?>() {}.type
        val entity: ListEntity<TransactionEntity> = gson.fromJson(fileContent, listType)
        enqueueContent(fileContent)

        val response = sut.getTransactions(cardId, filters)

        response.shouldBeRightAndEqualTo(entity.data?.map { it.toTransaction() })
    }
}
