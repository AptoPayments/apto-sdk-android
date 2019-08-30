package com.aptopayments.core.repository.card

import com.aptopayments.core.UnitTest
import com.aptopayments.core.data.TestDataProvider
import com.aptopayments.core.data.card.Card
import com.aptopayments.core.exception.Failure.NetworkConnection
import com.aptopayments.core.exception.Failure.ServerError
import com.aptopayments.core.functional.Either
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.UserSessionRepository
import com.aptopayments.core.repository.card.local.CardBalanceLocalDao
import com.aptopayments.core.repository.card.local.CardLocalDao
import com.aptopayments.core.repository.card.remote.CardService
import com.aptopayments.core.repository.card.remote.entities.CardEntity
import com.aptopayments.core.repository.card.remote.requests.IssueCardRequest
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

class CardRepositoryTest : UnitTest() {
    private lateinit var sut: CardRepository.Network

    // Collaborators
    @Mock private lateinit var networkHandler: NetworkHandler
    @Mock private lateinit var service: CardService
    @Mock private lateinit var cardLocalDao: CardLocalDao
    @Mock private lateinit var cardBalanceLocalDao: CardBalanceLocalDao
    @Mock private lateinit var userSessionRepository: UserSessionRepository
    // Issue card
    @Mock private lateinit var issueCardCall: Call<CardEntity>
    @Mock private lateinit var issueCardResponse: Response<CardEntity>

    @Before
    override fun setUp() {
        super.setUp()
        startKoin {
            modules(module {
                single { networkHandler }
                single { service }
                single { cardLocalDao }
                single { cardBalanceLocalDao }
                single { userSessionRepository }
            })
        }
        sut = CardRepository.Network(networkHandler, service, cardLocalDao, cardBalanceLocalDao, userSessionRepository)
    }

    @Test
    fun `issue card return network failure when no connection`() {
        // Given
        given { networkHandler.isConnected }.willReturn(false)

        // When
        val result = sut.issueCard(cardProductId = "cardProductId",
                credential = null, additionalFields = null, initialFundingSourceId = null)

        // Then
        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `issue card return network failure when network state is unknown`() {
        // Given
        given { networkHandler.isConnected }.willReturn(null)

        // When
        val result = sut.issueCard(cardProductId = "cardProductId",
                credential = null, additionalFields = null, initialFundingSourceId = null)


        // Then
        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `issue card with network connection call service`() {
        // Given
        givenSetUpIssueCardMocks(willRequestSucceed = true)

        // When
        sut.issueCard(cardProductId = "cardProductId", credential = null, additionalFields = null,
                initialFundingSourceId = null)

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
        sut.issueCard(cardProductId = "cardProductId", credential = null,
                additionalFields = additionalFields, initialFundingSourceId = null)

        // Then
        verify(service).issueCard(IssueCardRequest(
                cardProductId = "cardProductId",
                oAuthCredentialRequest = null,
                additionalFields = additionalFields)
        )
    }

    @Test
    fun `issue card with initial funding source id pass initial funding source id`() {

        // Given
        val initialFundingSourceId = "initial_funding_source_id"
        givenSetUpIssueCardMocks(willRequestSucceed = true)

        // When
        sut.issueCard(cardProductId = "cardProductId",  credential = null, additionalFields = null,
                initialFundingSourceId = initialFundingSourceId)

        // Then
        verify(service).issueCard(IssueCardRequest(
                cardProductId = "cardProductId",
                oAuthCredentialRequest = null,
                additionalFields = null,
                initialFundingSourceId = initialFundingSourceId)
        )
    }

    @Test
    fun `issue card with network connection and request succeed return Right`() {
        // Given
        givenSetUpIssueCardMocks(willRequestSucceed = true)

        // When
        val result = sut.issueCard(cardProductId = "cardProductId",
                credential = null, additionalFields = null,  initialFundingSourceId = null)

        // Then
        result shouldBeInstanceOf Either::class.java
        result.isRight shouldEqual true
        result.either({}, { card -> card shouldBeInstanceOf Card::class.java })
    }

    @Test
    fun `issue card with network connection and request fails return Left`() {
        // Given
        givenSetUpIssueCardMocks(willRequestSucceed = false)

        // When
        val result = sut.issueCard(cardProductId = "cardProductId",
                credential = null, additionalFields = null,  initialFundingSourceId = null)

        // Then
        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
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
