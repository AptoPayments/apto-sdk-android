package com.aptopayments.mobile.repository.voip

import com.aptopayments.mobile.data.voip.Action
import com.aptopayments.mobile.repository.voip.remote.VoipService
import com.aptopayments.mobile.repository.voip.remote.requests.GetTokensRequest
import com.aptopayments.mobile.repository.voip.usecases.SetupVoipCallParams
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.junit.jupiter.api.Test

private const val CARD_ID = "id_12345"

internal class VoipRepositoryImplTest {

    private val service: VoipService = mock()

    private val sut = VoipRepositoryImpl(service)

    @Test
    fun `whenever setupVoIPCall with listenPIN then service gets called correctly`() {
        sut.setupVoIPCall(SetupVoipCallParams(CARD_ID, Action.LISTEN_PIN))

        verify(service).getTokens(GetTokensRequest(CARD_ID, Action.LISTEN_PIN.source))
    }

    @Test
    fun `whenever setupVoIPCall with customerSupport then service gets called correctly`() {
        sut.setupVoIPCall(SetupVoipCallParams(CARD_ID, Action.CUSTOMER_SUPPORT))

        verify(service).getTokens(GetTokensRequest(CARD_ID, Action.CUSTOMER_SUPPORT.source))
    }
}
