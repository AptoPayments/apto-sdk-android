package com.aptopayments.mobile.repository.voip.remote

import com.aptopayments.mobile.NetworkServiceTest
import com.aptopayments.mobile.repository.voip.remote.requests.GetTokensRequest
import org.junit.Test
import org.koin.test.inject

internal class VoipServiceTest : NetworkServiceTest() {

    val sut: VoipService by inject()

    @Test
    fun `when getMonthlyStatementPeriod then request is made to the correct url`() {
        enqueueFile("userStatementsPeriodResponse200.json")

        sut.getTokens(GetTokensRequest("id", "action"))

        assertRequestSentTo("v1/voip/authorization", "POST")
    }
}
