package com.aptopayments.mobile.repository.voip.usecases

import com.aptopayments.mobile.data.voip.Action
import com.aptopayments.mobile.data.voip.VoipCall
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.ConnectivityCheckerAlwaysConnected
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.voip.VoipRepository
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SetupVoipCallUseCaseTest {

    private lateinit var sut: SetupVoipCallUseCase

    private val mockCall: VoipCall = mock()
    private val repository: VoipRepository = mock()

    @BeforeEach
    fun setUp() {
        sut = SetupVoipCallUseCase(repository, NetworkHandler(ConnectivityCheckerAlwaysConnected()))
    }

    @Test
    fun `should get token through the repository`() {
        val cardID = "TEST_CARD_ID"
        val action = Action.LISTEN_PIN
        val params = SetupVoipCallParams(cardID, action)
        given { repository.setupVoIPCall(params) }.willReturn(Either.Right(mockCall))

        sut.run(params)

        verify(repository).setupVoIPCall(params)
        verifyNoMoreInteractions(repository)
    }
}
