package com.aptopayments.mobile.repository.card

import com.aptopayments.mobile.UnitTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.exception.Failure.NetworkConnection
import com.aptopayments.mobile.exception.Failure.ServerError
import com.aptopayments.mobile.extension.shouldBeLeftAndInstanceOf
import com.aptopayments.mobile.extension.shouldBeRightAndInstanceOf
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.platform.ErrorHandler
import com.aptopayments.mobile.platform.RequestExecutor
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.card.local.CardBalanceLocalDao
import com.aptopayments.mobile.repository.card.local.CardLocalRepository
import com.aptopayments.mobile.repository.card.remote.CardService
import com.aptopayments.mobile.repository.card.remote.entities.CardEntity
import com.aptopayments.mobile.repository.card.remote.requests.IssueCardRequest
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

class CardRepositoryTest : UnitTest() {

    private lateinit var requestExecutor: RequestExecutor
    private lateinit var sut: CardRepository.Network

    // Collaborators
    @Mock
    private lateinit var networkHandler: NetworkHandler

    @Mock
    private lateinit var service: CardService

    @Mock
    private lateinit var cardLocalRepository: CardLocalRepository

    @Mock
    private lateinit var cardBalanceLocalDao: CardBalanceLocalDao

    @Mock
    private lateinit var userSessionRepository: UserSessionRepository

    // Issue card
    @Mock
    private lateinit var issueCardCall: Call<CardEntity>

    @Mock
    private lateinit var issueCardResponse: Response<CardEntity>

    @Before
    override fun setUp() {
        super.setUp()
        startKoin {
            modules(
                module {
                    single { networkHandler }
                    single { service }
                    single { cardLocalRepository }
                    single { cardBalanceLocalDao }
                    single { userSessionRepository }
                    single { requestExecutor }
                }
            )
        }
        requestExecutor = RequestExecutor(networkHandler, ErrorHandler(mock()))
        sut = CardRepository.Network(
            networkHandler,
            service,
            cardLocalRepository,
            cardBalanceLocalDao,
            userSessionRepository
        )
    }

    @Test
    fun `issue card return network failure when no connection`() {
        // Given
        given { networkHandler.isConnected }.willReturn(false)

        // When
        val result = sut.issueCard(
            cardProductId = "cardProductId",
            credential = null, additionalFields = null, initialFundingSourceId = null
        )

        // Then
        result.shouldBeLeftAndInstanceOf(NetworkConnection::class.java)
        verifyZeroInteractions(service)
    }

    @Test
    fun `issue card with network connection call service`() {
        // Given
        givenSetUpIssueCardMocks(willRequestSucceed = true)

        // When
        sut.issueCard(
            cardProductId = "cardProductId", credential = null, additionalFields = null,
            initialFundingSourceId = null
        )

        // Then
        verify(service).issueCard(TestDataProvider.anyObject())
    }

    @Test
    fun `issue card with additional fields pass additional fields`() {
        // Given
        val nestedMap = mapOf<String, Any>("nestedField1" to "nestedValue1")
        val additionalFields = mapOf("field1" to "value1", "field2" to 2, "nested" to nestedMap)
        givenSetUpIssueCardMocks(willRequestSucceed = true)

        // When
        sut.issueCard(
            cardProductId = "cardProductId", credential = null,
            additionalFields = additionalFields, initialFundingSourceId = null
        )

        // Then
        verify(service).issueCard(
            IssueCardRequest(
                cardProductId = "cardProductId",
                oAuthCredentialRequest = null,
                additionalFields = additionalFields
            )
        )
    }

    @Test
    fun `issue card with initial funding source id pass initial funding source id`() {

        // Given
        val initialFundingSourceId = "initial_funding_source_id"
        givenSetUpIssueCardMocks(willRequestSucceed = true)

        // When
        sut.issueCard(
            cardProductId = "cardProductId", credential = null, additionalFields = null,
            initialFundingSourceId = initialFundingSourceId
        )

        // Then
        verify(service).issueCard(
            IssueCardRequest(
                cardProductId = "cardProductId",
                oAuthCredentialRequest = null,
                additionalFields = null,
                initialFundingSourceId = initialFundingSourceId
            )
        )
    }

    @Test
    fun `issue card with network connection and request succeed return Right`() {
        // Given
        givenSetUpIssueCardMocks(willRequestSucceed = true)

        // When
        val result = sut.issueCard(
            cardProductId = "cardProductId",
            credential = null, additionalFields = null, initialFundingSourceId = null
        )

        // Then
        result.shouldBeRightAndInstanceOf(Card::class.java)
    }

    @Test
    fun `issue card with network connection and request fails return Left`() {
        // Given
        givenSetUpIssueCardMocks(willRequestSucceed = false)

        // When
        val result = sut.issueCard(
            cardProductId = "cardProductId",
            credential = null, additionalFields = null, initialFundingSourceId = null
        )

        // Then
        result.shouldBeLeftAndInstanceOf(ServerError::class.java)
    }

    // Helpers
    private fun givenSetUpIssueCardMocks(willRequestSucceed: Boolean) {
        given { networkHandler.isConnected }.willReturn(true)
        given { issueCardResponse.body() }.willReturn(CardEntity())
        given { issueCardResponse.isSuccessful }.willReturn(willRequestSucceed)
        given { issueCardCall.execute() }.willReturn(issueCardResponse)
        given { service.issueCard(TestDataProvider.anyObject()) }.willReturn(issueCardCall)
    }
}
