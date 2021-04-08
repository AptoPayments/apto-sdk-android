package com.aptopayments.mobile.repository

import com.aptopayments.mobile.UnitTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.data.oauth.OAuthAttempt
import com.aptopayments.mobile.data.oauth.OAuthAttemptStatus
import com.aptopayments.mobile.data.oauth.OAuthUserDataUpdate
import com.aptopayments.mobile.data.oauth.OAuthUserDataUpdateResult
import com.aptopayments.mobile.data.workflowaction.AllowedBalanceType
import com.aptopayments.mobile.exception.server.ServerErrorFactory
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.platform.ErrorHandler
import com.aptopayments.mobile.platform.RequestExecutor
import com.aptopayments.mobile.repository.oauth.OAuthRepositoryImpl
import com.aptopayments.mobile.repository.oauth.remote.OAuthService
import com.aptopayments.mobile.repository.oauth.remote.entities.OAuthAttemptEntity
import com.aptopayments.mobile.repository.oauth.remote.entities.OAuthUserDataUpdateEntity
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.mockito.Mock
import kotlin.test.assertEquals

class OAuthConnectRepositoryTest : UnitTest() {

    private lateinit var requestExecutor: RequestExecutor
    private lateinit var sut: OAuthRepositoryImpl

    @Mock
    private lateinit var networkHandler: NetworkHandler

    @Mock
    private lateinit var service: OAuthService

    @Mock
    private lateinit var mockUserSessionRepository: UserSessionRepository

    @Before
    override fun setUp() {
        super.setUp()
        requestExecutor = RequestExecutor(networkHandler, ErrorHandler(mock(), ServerErrorFactory()))
        startKoin {
            modules(
                module {
                    single { mockUserSessionRepository }
                    single { requestExecutor }
                }
            )
        }
        sut = OAuthRepositoryImpl(service)
    }

    @Test
    fun `should start OAuth Authentication from service`() {
        val allowedBalanceType = TestDataProvider.provideAllowedBalanceType()
        val entity = OAuthAttemptEntity()
        given { service.startOAuthAuthentication(allowedBalanceType = allowedBalanceType) }
            .willReturn(entity.toOAuthAttempt().right())

        val oauthAttempt = sut.startOAuthAuthentication(allowedBalanceType)

        assertEquals(getPendingOauthAttempt(), oauthAttempt)

        verify(service).startOAuthAuthentication(allowedBalanceType = allowedBalanceType)
    }

    @Test
    fun `should save OAuth user data from service`() {
        val allowedBalanceType = TestDataProvider.provideAllowedBalanceType()
        val oAuthAttempt = TestDataProvider.provideOAuthAttempt()
        val entity = OAuthUserDataUpdateEntity()
        given {
            service.saveOAuthUserData(
                allowedBalanceType = allowedBalanceType,
                dataPointList = oAuthAttempt.userData!!,
                tokenId = oAuthAttempt.tokenId
            )
        }
            .willReturn(entity.toOAuthUserDataUpdate().right())

        val oAuthUserDataUpdate = sut.saveOAuthUserData(
            allowedBalanceType,
            dataPointList = oAuthAttempt.userData!!,
            tokenId = oAuthAttempt.tokenId
        )

        assertEquals(getInvalidOauthUserDataUpdate(), oAuthUserDataUpdate)

        verify(service).saveOAuthUserData(
            allowedBalanceType = allowedBalanceType,
            dataPointList = oAuthAttempt.userData!!,
            tokenId = oAuthAttempt.tokenId
        )
    }

    @Test
    fun `when getOAuthAttemptStatus correct data is fetched`() {
        val attemptId = "attempt_123"
        val attempt: OAuthAttempt = mock()
        whenever(service.getOAuthAttemptStatus(attemptId)).thenReturn(attempt.right())

        val result = sut.getOAuthAttemptStatus(attemptId)

        verify(service).getOAuthAttemptStatus(attemptId)
        result.shouldBeRightAndEqualTo(attempt)
    }

    @Test
    fun `when retrieveOAuthUserData correct data is fetched`() {
        val tokenId = "token_123"
        val balanceType: AllowedBalanceType = mock()
        val update: OAuthUserDataUpdate = mock()
        whenever(service.retrieveOAuthUserData(balanceType, tokenId)).thenReturn(update.right())

        val result = sut.retrieveOAuthUserData(balanceType, tokenId)

        verify(service).retrieveOAuthUserData(balanceType, tokenId)
        result.shouldBeRightAndEqualTo(update)
    }

    private fun getPendingOauthAttempt(): Either<Nothing, OAuthAttempt> {
        return OAuthAttempt(
            id = "",
            status = OAuthAttemptStatus.PENDING,
            url = null,
            userData = null,
            tokenId = "",
            error = null,
            errorMessage = null
        ).right()
    }

    private fun getInvalidOauthUserDataUpdate(): Either<Nothing, OAuthUserDataUpdate> {
        return OAuthUserDataUpdate(
            result = OAuthUserDataUpdateResult.INVALID,
            userData = null
        ).right()
    }
}
