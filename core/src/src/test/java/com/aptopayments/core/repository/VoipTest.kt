package com.aptopayments.core.repository

import android.app.Activity
import com.aptopayments.core.UnitTest
import com.aptopayments.core.data.voip.Action
import com.aptopayments.core.data.voip.VoipCall
import com.aptopayments.core.functional.Either.Right
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.voip.VoipRepository
import com.aptopayments.core.repository.voip.usecases.SetupVoipCallParams
import com.aptopayments.core.repository.voip.usecases.SetupVoipCallUseCase
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class VoipTest : UnitTest() {

    private lateinit var sut: SetupVoipCallUseCase
    @Mock private lateinit var mockCall: VoipCall
    @Mock private lateinit var repository: VoipRepository

    @Before
    override fun setUp() {
        super.setUp()
        sut = SetupVoipCallUseCase(repository, NetworkHandler(Activity()))
    }

    @Test fun `should get token through the repository`() {
        // Given
        val cardID = "TEST_CARD_ID"
        val action = Action.LISTEN_PIN
        val params = SetupVoipCallParams(cardID, action)
        given { repository.setupVoIPCall(params) }.willReturn(Right(mockCall))

        // When
        runBlocking { sut.run(params) }

        // Then
        verify(repository).setupVoIPCall(params)
        verifyNoMoreInteractions(repository)
    }

}
