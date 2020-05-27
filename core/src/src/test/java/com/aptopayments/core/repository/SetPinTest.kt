package com.aptopayments.core.repository

import com.aptopayments.core.UnitTest
import com.aptopayments.core.data.card.Card
import com.aptopayments.core.functional.Either.Right
import com.aptopayments.core.network.ConnectivityCheckerAlwaysConnected
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.card.CardRepository
import com.aptopayments.core.repository.card.usecases.SetPinParams
import com.aptopayments.core.repository.card.usecases.SetPinUseCase
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class SetPinTest : UnitTest() {

    private lateinit var sut: SetPinUseCase
    @Mock
    private lateinit var mockCard: Card
    @Mock
    private lateinit var repository: CardRepository

    @Before
    override fun setUp() {
        super.setUp()
        sut = SetPinUseCase(repository, NetworkHandler(ConnectivityCheckerAlwaysConnected()))
    }

    @Test
    fun `should set pin through the repository`() {
        // Given
        val testPin = "1234"
        val testCardId = ""
        val setPinParams = SetPinParams(cardId = testCardId, pin = testPin)
        given { repository.setPin(params = setPinParams) }
            .willReturn(Right(mockCard))

        // When
        runBlocking { sut.run(params = setPinParams) }

        // Then
        verify(repository).setPin(setPinParams)
        verifyNoMoreInteractions(repository)
    }
}
