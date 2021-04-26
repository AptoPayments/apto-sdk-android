package com.aptopayments.mobile.repository

import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.functional.Either.Right
import com.aptopayments.mobile.network.ConnectivityCheckerAlwaysConnected
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.oauth.OAuthRepository
import com.aptopayments.mobile.repository.oauth.usecases.StartOAuthAuthenticationUseCase
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class StartOAuthAuthenticationTest {

    private lateinit var sut: StartOAuthAuthenticationUseCase

    private val oauthRepository: OAuthRepository = mock()

    @Before
    fun setUp() {
        sut = StartOAuthAuthenticationUseCase(oauthRepository, NetworkHandler(ConnectivityCheckerAlwaysConnected()))
    }

    @Test
    fun `should start oauth authentication through the repository`() {

        // Given
        val testOAuthAttempt = TestDataProvider.provideOAuthAttempt()
        val testAllowedBalanceType = TestDataProvider.provideAllowedBalanceType()
        given { oauthRepository.startOAuthAuthentication(testAllowedBalanceType) }
            .willReturn(Right(testOAuthAttempt))

        // When
        runBlocking { sut.run(testAllowedBalanceType) }

        // Then
        verify(oauthRepository).startOAuthAuthentication(testAllowedBalanceType)
        verifyNoMoreInteractions(oauthRepository)
    }
}
