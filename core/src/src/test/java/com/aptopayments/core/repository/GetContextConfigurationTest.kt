package com.aptopayments.core.repository

import com.aptopayments.core.UnitTest
import com.aptopayments.core.data.TestDataProvider
import com.aptopayments.core.functional.Either.Right
import com.aptopayments.core.network.ConnectivityCheckerAlwaysConnected
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.config.ConfigRepository
import com.aptopayments.core.repository.config.usecases.GetContextConfigurationUseCase
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetContextConfigurationTest : UnitTest() {

    private lateinit var sut: GetContextConfigurationUseCase

    @Mock
    private lateinit var repository: ConfigRepository

    @Before
    override fun setUp() {
        super.setUp()
        sut = GetContextConfigurationUseCase(repository, NetworkHandler(ConnectivityCheckerAlwaysConnected()))
    }

    @Test
    fun `should get config through the repository`() {

        // Given
        val testContextConfiguration =
            TestDataProvider.provideContextConfiguration()
        given { repository.getContextConfiguration() }
            .willReturn(Right(testContextConfiguration))

        // When
        runBlocking { sut.run(false) }

        // Then
        verify(repository).getContextConfiguration()
        verifyNoMoreInteractions(repository)
    }
}
