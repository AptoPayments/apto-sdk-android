package com.aptopayments.mobile.repository

import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.functional.Either.Right
import com.aptopayments.mobile.network.ConnectivityCheckerAlwaysConnected
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.card.CardRepository
import com.aptopayments.mobile.repository.card.usecases.SetPinParams
import com.aptopayments.mobile.repository.card.usecases.SetPinUseCase
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SetPinTest {

    private lateinit var sut: SetPinUseCase
    private val mockCard: Card = mock()
    private val repository: CardRepository = mock()

    @BeforeEach
    fun setUp() {
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
