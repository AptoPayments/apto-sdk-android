package com.aptopayments.core.repository.stats.remote

import com.aptopayments.core.ServiceTest
import com.aptopayments.core.data.TestDataProvider
import com.aptopayments.core.repository.stats.remote.entities.MonthlySpendingEntity
import org.junit.Assert.*
import org.junit.Test
import org.koin.core.inject
import java.net.HttpURLConnection.HTTP_OK
import java.time.LocalDate

internal class StatsServiceTest : ServiceTest() {

    private val sut: StatsService by inject()
    private val cardId = TestDataProvider.provideCardId()
    private val year = LocalDate.now().year.toString()
    private val month = "May"

    @Test
    fun `when startCardApplication then request is made to the correct url`() {
        enqueueFile("userAccountsStatsMonthlySpending200.json")

        val response = executeGetMonthlySpending()

        assertRequestSentTo("v1/user/accounts/$cardId/stats/monthly_spending?month=$month&year=$year", "GET")
        assertCode(response, HTTP_OK)
    }

    @Test
    fun `when issuecard then response parses correctly`() {
        val fileContent = readFile("userAccountsStatsMonthlySpending200.json")
        val cardEntity = parseEntity(fileContent, MonthlySpendingEntity::class.java)
        enqueueContent(fileContent)

        val response = executeGetMonthlySpending()

        assertEquals(cardEntity, response.body()!!)
    }

    private fun executeGetMonthlySpending() = sut.getMonthlySpending(cardId, month, year).execute()
}
