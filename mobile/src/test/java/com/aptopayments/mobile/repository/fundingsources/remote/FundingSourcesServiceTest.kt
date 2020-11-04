package com.aptopayments.mobile.repository.fundingsources.remote

import com.aptopayments.mobile.NetworkServiceTest
import org.junit.Test
import org.koin.test.inject
import kotlin.test.assertTrue

private const val ACCOUNT_ID = "id_12345"
private const val PAGE = 1
private const val ROWS = 10

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
}
