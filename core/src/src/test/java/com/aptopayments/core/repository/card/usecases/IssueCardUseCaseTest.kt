package com.aptopayments.core.repository.card.usecases

import com.aptopayments.core.UnitTest
import com.aptopayments.core.data.TestDataProvider
import com.aptopayments.core.data.card.Card
import com.aptopayments.core.data.oauth.OAuthCredential
import com.aptopayments.core.exception.Failure.ServerError
import com.aptopayments.core.functional.Either
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.card.CardRepository
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.willReturn
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class IssueCardUseCaseTest : UnitTest() {
    private lateinit var sut: IssueCardUseCase

    // Collaborators
    @Mock private lateinit var cardRepository: CardRepository
    @Mock private lateinit var networkHandler: NetworkHandler
    private val params = IssueCardUseCase.Params(
            cardProductId = "card_product_id",
            credential = OAuthCredential(oauthToken = "token", refreshToken = "refresh_token"),
            useBalanceV2 = true,
            additionalFields = null,
            initialFundingSourceId = null)

    @Before
    override fun setUp() {
        super.setUp()
        sut = IssueCardUseCase(cardRepository, networkHandler)
    }

    @Test
    fun `issue card call repository with the appropriate parameters`() {
        // When
        sut.run(params)

        // Then
        verify(cardRepository).issueCard(params.cardProductId, params.credential, params.useBalanceV2,
                params.additionalFields, params.initialFundingSourceId)
    }

    @Test
    fun `repository return success sut return success`() {
        // Given
        given { cardRepository.issueCard(params.cardProductId, params.credential, params.useBalanceV2,
                params.additionalFields, params.initialFundingSourceId)
        }.willReturn { Either.Right(TestDataProvider.provideCard()) }

        // When
        val result = sut.run(params)

        // Then
        result shouldBeInstanceOf Either::class.java
        result.isRight shouldEqual true
        result.either({}, { card -> card shouldBeInstanceOf Card::class.java })
    }

    @Test
    fun `repository return failure sut return failure`() {
        // Given
        given { cardRepository.issueCard(params.cardProductId, params.credential, params.useBalanceV2,
                params.additionalFields, params.initialFundingSourceId)
        }.willReturn { Either.Left(ServerError(errorCode = null)) }

        // When
        val result = sut.run(params)

        // Then
        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }
}
