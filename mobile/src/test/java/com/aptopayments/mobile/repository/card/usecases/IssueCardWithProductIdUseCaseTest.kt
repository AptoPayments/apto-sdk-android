package com.aptopayments.mobile.repository.card.usecases

import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.data.oauth.OAuthCredential
import com.aptopayments.mobile.exception.Failure.ServerError
import com.aptopayments.mobile.extension.shouldBeLeftAndInstanceOf
import com.aptopayments.mobile.extension.shouldBeRightAndInstanceOf
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.card.CardRepository
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.willReturn
import org.junit.Before
import org.junit.Test

class IssueCardWithProductIdUseCaseTest {
    private lateinit var sut: IssueCardWithProductIdUseCase

    // Collaborators
    private val cardRepository: CardRepository = mock()
    private val networkHandler: NetworkHandler = mock()
    private val params = IssueCardWithProductIdUseCase.Params(
        cardProductId = "card_product_id",
        credential = OAuthCredential(oauthToken = "token", refreshToken = "refresh_token"),
        initialFundingSourceId = null
    )

    @Before
    fun setUp() {
        sut = IssueCardWithProductIdUseCase(cardRepository, networkHandler)
    }

    @Test
    fun `issue card call repository with the appropriate parameters`() {
        // When
        sut.run(params)

        // Then
        verify(cardRepository).issueCard(
            params.cardProductId,
            params.credential,
            params.initialFundingSourceId
        )
    }

    @Test
    fun `repository return success sut return success`() {
        // Given
        given {
            cardRepository.issueCard(
                params.cardProductId,
                params.credential,
                params.initialFundingSourceId
            )
        }.willReturn { Either.Right(TestDataProvider.provideCard()) }

        // When
        val result = sut.run(params)

        // Then
        result.shouldBeRightAndInstanceOf(Card::class.java)
    }

    @Test
    fun `repository return failure sut return failure`() {
        // Given
        given {
            cardRepository.issueCard(
                params.cardProductId,
                params.credential,
                params.initialFundingSourceId
            )
        }.willReturn { Either.Left(ServerError(code = null)) }

        // When
        val result = sut.run(params)

        // Then
        result.shouldBeLeftAndInstanceOf(ServerError::class.java)
    }
}
