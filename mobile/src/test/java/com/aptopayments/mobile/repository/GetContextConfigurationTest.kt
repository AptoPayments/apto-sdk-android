package com.aptopayments.mobile.repository

import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.functional.Either.Right
import com.aptopayments.mobile.network.ConnectivityCheckerAlwaysConnected
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.config.ConfigRepository
import com.aptopayments.mobile.repository.config.usecases.GetContextConfigurationUseCase
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetContextConfigurationTest {

    private lateinit var sut: GetContextConfigurationUseCase

    private val repository: ConfigRepository = mock()

    @Before
    fun setUp() {
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
