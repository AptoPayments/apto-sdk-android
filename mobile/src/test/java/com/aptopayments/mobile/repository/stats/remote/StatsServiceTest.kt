package com.aptopayments.mobile.repository.stats.remote

import com.aptopayments.mobile.NetworkServiceTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.repository.stats.remote.entities.MonthlySpendingEntity
import org.junit.jupiter.api.Test
import org.koin.core.inject
import java.time.LocalDate
import kotlin.test.assertTrue

internal class StatsServiceTest : NetworkServiceTest() {

    private val sut: StatsService by inject()
    private val cardId = TestDataProvider.provideCardId()
    private val year = LocalDate.now().year.toString()
    private val month = "May"

    @Test
    fun `when startCardApplication then request is made to the correct url`() {
        enqueueFile("userAccountsStatsMonthlySpending200.json")

        val response = sut.getMonthlySpending(cardId, month, year)

        assertRequestSentTo("v1/user/accounts/$cardId/stats/monthly_spending?month=$month&year=$year", "GET")
        assertTrue(response.isRight)
    }

    @Test
    fun `when startCardApplication then response parses correctly`() {
        val fileContent = readFile("userAccountsStatsMonthlySpending200.json")
        val cardEntity = parseEntity(fileContent, MonthlySpendingEntity::class.java)
        enqueueContent(fileContent)

        val response = sut.getMonthlySpending(cardId, month, year)

        response.shouldBeRightAndEqualTo(cardEntity.toMonthlySpending())
    }
}
