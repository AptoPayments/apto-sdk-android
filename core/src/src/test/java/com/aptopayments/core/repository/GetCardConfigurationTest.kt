package com.aptopayments.core.repository

import com.aptopayments.core.UnitTest
import com.aptopayments.core.data.TestDataProvider
import com.aptopayments.core.functional.Either.Right
import com.aptopayments.core.network.ConnectivityCheckerAlwaysConnected
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.config.ConfigRepository
import com.aptopayments.core.repository.config.usecases.GetCardProductParams
import com.aptopayments.core.repository.config.usecases.GetCardProductUseCase
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetCardConfigurationTest : UnitTest() {

    private lateinit var sut: GetCardProductUseCase

    @Mock
    private lateinit var repository: ConfigRepository

    @Before
    override fun setUp() {
        super.setUp()
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
