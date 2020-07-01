package com.aptopayments.mobile.repository

import com.aptopayments.mobile.UnitTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.data.oauth.OAuthAttempt
import com.aptopayments.mobile.data.oauth.OAuthAttemptStatus
import com.aptopayments.mobile.data.oauth.OAuthUserDataUpdate
import com.aptopayments.mobile.data.oauth.OAuthUserDataUpdateResult
import com.aptopayments.mobile.exception.Failure.NetworkConnection
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.functional.Either.Right
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.oauth.OAuthRepository
import com.aptopayments.mobile.repository.oauth.remote.OAuthService
import com.aptopayments.mobile.repository.oauth.remote.entities.OAuthAttemptEntity
import com.aptopayments.mobile.repository.oauth.remote.entities.OAuthUserDataUpdateEntity
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

class OAuthConnectRepositoryTest : UnitTest() {

    private lateinit var sut: OAuthRepository.Network

    @Mock
    private lateinit var networkHandler: NetworkHandler
    @Mock
    private lateinit var service: OAuthService
    @Mock
    private lateinit var mockUserSessionRepository: UserSessionRepository

    @Mock
    private lateinit var startOAuthCall: Call<OAuthAttemptEntity>
    @Mock
    private lateinit var startOAuthResponse: Response<OAuthAttemptEntity>
    @Mock
    private lateinit var saveOAuthUserDataCall: Call<OAuthUserDataUpdateEntity>
    @Mock
    private lateinit var saveOAuthUserDataResponse: Response<OAuthUserDataUpdateEntity>

    @Before
    override fun setUp() {
        super.setUp()
        startKoin {
            modules(module {
                single { mockUserSessionRepository }
            })
        }
        sut = OAuthRepository.Network(networkHandler, service)
    }

    @Test
    fun `should start OAuth Authentication from service`() {
        val allowedBalanceType = TestDataProvider.provideAllowedBalanceType()
        given { networkHandler.isConnected }.willReturn(true)
        given { startOAuthResponse.body() }.willReturn(OAuthAttemptEntity())
        given { startOAuthResponse.isSuccessful }.willReturn(true)
        given { startOAuthCall.execute() }.willReturn(startOAuthResponse)
        given { service.startOAuthAuthentication(allowedBalanceType = allowedBalanceType) }
            .willReturn(startOAuthCall)

        val oauthAttempt = sut.startOAuthAuthentication(allowedBalanceType)

        oauthAttempt shouldEqual Right(
            OAuthAttempt(
                id = "",
                status = OAuthAttemptStatus.PENDING,
                url = null,
                userData = null,
                tokenId = "",
                error = null,
                errorMessage = null
            )
        )

        verify(service).startOAuthAuthentication(allowedBalanceType = allowedBalanceType)
    }

    @Test
    fun `OAuth connect service should return network failure when no connection`() {
        val allowedBalanceType = TestDataProvider.provideAllowedBalanceType()
        given { networkHandler.isConnected }.willReturn(false)

        val result = sut.startOAuthAuthentication(allowedBalanceType)

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `OAuth connect service should return network failure when undefined connection`() {
        val allowedBalanceType = TestDataProvider.provideAllowedBalanceType()
        given { networkHandler.isConnected }.willReturn(false)

        val result = sut.startOAuthAuthentication(allowedBalanceType)

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `should save OAuth user data from service`() {
        val allowedBalanceType = TestDataProvider.provideAllowedBalanceType()
        val oAuthAttempt = TestDataProvider.provideOAuthAttempt()
        given { networkHandler.isConnected }.willReturn(true)
        given { saveOAuthUserDataResponse.body() }.willReturn(OAuthUserDataUpdateEntity())
        given { saveOAuthUserDataResponse.isSuccessful }.willReturn(true)
        given { saveOAuthUserDataCall.execute() }.willReturn(saveOAuthUserDataResponse)
        given {
            service.saveOAuthUserData(
                allowedBalanceType = allowedBalanceType,
                dataPointList = oAuthAttempt.userData!!,
                tokenId = oAuthAttempt.tokenId
            )
        }
            .willReturn(saveOAuthUserDataCall)

        val oAuthUserDataUpdate = sut.saveOAuthUserData(
            allowedBalanceType,
            dataPointList = oAuthAttempt.userData!!,
            tokenId = oAuthAttempt.tokenId
        )

        oAuthUserDataUpdate shouldEqual Right(
            OAuthUserDataUpdate(
                result = OAuthUserDataUpdateResult.INVALID,
                userData = null
            )
        )

        verify(service).saveOAuthUserData(
            allowedBalanceType = allowedBalanceType,
            dataPointList = oAuthAttempt.userData!!,
            tokenId = oAuthAttempt.tokenId
        )
    }

    @Test
    fun `save OAuth user data should return network failure when no connection`() {
        val allowedBalanceType = TestDataProvider.provideAllowedBalanceType()
        val oAuthAttempt = TestDataProvider.provideOAuthAttempt()
        given { networkHandler.isConnected }.willReturn(false)

        val result = sut.saveOAuthUserData(
            allowedBalanceType,
            dataPointList = oAuthAttempt.userData!!,
            tokenId = oAuthAttempt.tokenId
        )

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }
}
