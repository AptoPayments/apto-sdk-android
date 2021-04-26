package com.aptopayments.mobile.repository.voip.usecases

import com.aptopayments.mobile.data.voip.Action
import com.aptopayments.mobile.data.voip.VoipCall
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.ConnectivityCheckerAlwaysConnected
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.voip.VoipRepository
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Test

class SetupVoipCallUseCaseTest {

    private lateinit var sut: SetupVoipCallUseCase

    private val mockCall: VoipCall = mock()
    private val repository: VoipRepository = mock()

    @Before
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
