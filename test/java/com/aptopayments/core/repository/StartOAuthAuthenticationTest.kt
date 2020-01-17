package com.aptopayments.core.repository

import android.app.Activity
import com.aptopayments.core.UnitTest
import com.aptopayments.core.data.TestDataProvider
import com.aptopayments.core.functional.Either.Right
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.oauth.OAuthRepository
import com.aptopayments.core.repository.oauth.usecases.StartOAuthAuthenticationUseCase
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class StartOAuthAuthenticationTest : UnitTest() {

    private lateinit var sut: StartOAuthAuthenticationUseCase

    @Mock private lateinit var oauthRepository: OAuthRepository

    @Before
    override fun setUp() {
        super.setUp()
        sut = StartOAuthAuthenticationUseCase(oauthRepository, NetworkHandler(Activity()))
    }

    @Test fun `should start oauth authentication through the repository`() {

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
