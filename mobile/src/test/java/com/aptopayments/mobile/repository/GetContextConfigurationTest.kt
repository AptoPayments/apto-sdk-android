package com.aptopayments.mobile.repository

import com.aptopayments.mobile.UnitTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.functional.Either.Right
import com.aptopayments.mobile.network.ConnectivityCheckerAlwaysConnected
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.config.ConfigRepository
import com.aptopayments.mobile.repository.config.usecases.GetContextConfigurationUseCase
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
