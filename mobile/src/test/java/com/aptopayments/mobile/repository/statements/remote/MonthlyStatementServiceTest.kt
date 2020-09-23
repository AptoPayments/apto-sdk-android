package com.aptopayments.mobile.repository.statements.remote

import com.aptopayments.mobile.NetworkServiceTest
import com.aptopayments.mobile.common.ModelDataProvider
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import org.junit.Test
import org.koin.test.inject

private const val MONTH = 12
private const val YEAR = 2019

internal class MonthlyStatementServiceTest : NetworkServiceTest() {

    val sut: MonthlyStatementService by inject()

    @Test
    fun `when getMonthlyStatementPeriod then request is made to the correct url`() {
        enqueueFile("userStatementsPeriodResponse200.json")

        sut.getMonthlyStatementPeriod()

        assertRequestSentTo("v1/user/statements/period", "GET")
    }

    @Test
    fun `when getMonthlyStatementPeriod then response parses correctly`() {
        val fileContent = readFile("userStatementsPeriodResponse200.json")
        enqueueContent(fileContent)

        val response = sut.getMonthlyStatementPeriod()

        response.shouldBeRightAndEqualTo(ModelDataProvider.monthlyStatementsPeriod())
    }

    @Test
    fun `when getMonthlyStatement then request is made to the correct url`() {
        enqueueFile("userStatementsResponse200.json")

        sut.getMonthlyStatement(MONTH, YEAR)

        assertRequestSentTo("v1/user/statements", "POST")
    }

    @Test
    fun `when startCardApplication then request is made correctly`() {
        enqueueFile("userStatementsResponse200.json")

        sut.getMonthlyStatement(MONTH, YEAR)

        assertRequestBodyFile("userStatementsRequest.json")
    }

    @Test
    fun `when getMonthlyStatement then response parses correctly`() {
        val fileContent = readFile("userStatementsResponse200.json")
        enqueueContent(fileContent)

        val response = sut.getMonthlyStatement(MONTH, YEAR)

        response.shouldBeRightAndEqualTo(ModelDataProvider.monthlyStatement())
    }
}
