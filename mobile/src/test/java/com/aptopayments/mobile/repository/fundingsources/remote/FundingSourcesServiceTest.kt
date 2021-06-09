package com.aptopayments.mobile.repository.fundingsources.remote

import com.aptopayments.mobile.NetworkServiceTest
import com.aptopayments.mobile.data.fundingsources.AchAccountDetails
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import org.junit.jupiter.api.Test
import org.koin.test.inject
import kotlin.test.assertTrue

private const val ACCOUNT_ID = "id_12345"
private const val PAGE = 1
private const val ROWS = 10
private const val BALANCE_ID = "id_1234567"

internal class FundingSourcesServiceTest : NetworkServiceTest() {

    val sut: FundingSourcesService by inject()

    @Test
    fun `when getMonthlyStatementPeriod then request is made to the correct url`() {
        enqueueContent("{}")

        sut.getFundingSources(ACCOUNT_ID, PAGE, ROWS)

        assertRequestSentTo("v1/user/accounts/$ACCOUNT_ID/balances?page=$PAGE&rows=$ROWS", "GET")
    }

    @Test
    fun `when getMonthlyStatementPeriod then response parses correctly`() {
        enqueueContent("{}")

        val result = sut.getFundingSources(ACCOUNT_ID, PAGE, ROWS)

        assertTrue { result.isRight }
    }

    @Test
    fun `when setupAchAccountForBalance then request is made to the correct url`() {
        enqueueContent("achAccountDetails200.json")

        sut.assignAchAccountToBalance(BALANCE_ID)

        assertRequestSentTo("v1/balances/$BALANCE_ID/ach", "POST")
    }

    @Test
    fun `when setupAchAccountForBalance then response parses correctly`() {
        val details = AchAccountDetails("asd_12345", "asd_56789")
        enqueueFile("achAccountDetails200.json")

        val result = sut.assignAchAccountToBalance(BALANCE_ID)

        result.shouldBeRightAndEqualTo(details)
    }

    @Test
    fun `when getAchDetails then request is made to the correct url`() {
        enqueueContent("{}")

        sut.getAchAccountDetails(BALANCE_ID)

        assertRequestSentTo("v1/balances/$BALANCE_ID/ach", "GET")
    }

    @Test
    fun `when getAchDetails then response parses correctly`() {
        val details = AchAccountDetails("asd_12345", "asd_56789")
        enqueueFile("achAccountDetails200.json")

        val result = sut.getAchAccountDetails(BALANCE_ID)

        result.shouldBeRightAndEqualTo(details)
    }
}
