package com.aptopayments.mobile.repository

import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.functional.Either.Right
import com.aptopayments.mobile.network.ConnectivityCheckerAlwaysConnected
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.config.ConfigRepository
import com.aptopayments.mobile.repository.config.usecases.GetCardProductParams
import com.aptopayments.mobile.repository.config.usecases.GetCardProductUseCase
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetCardConfigurationTest {

    private lateinit var sut: GetCardProductUseCase

    private val repository: ConfigRepository = mock()

    @BeforeEach
    fun setUp() {
        sut = GetCardProductUseCase(repository, NetworkHandler(ConnectivityCheckerAlwaysConnected()))
    }

    @Test
    fun `should get card config through the repository`() {
        // Given
        val testConfig = TestDataProvider.provideCardProduct()
        val testCardProductId = ""

        given { repository.getCardProduct(cardProductId = testCardProductId) }
            .willReturn(Right(testConfig))

        // When
        runBlocking { sut.run(params = GetCardProductParams(false, testCardProductId)) }

        // Then
        verify(repository).getCardProduct(testCardProductId, false)
        verifyNoMoreInteractions(repository)
    }
}
