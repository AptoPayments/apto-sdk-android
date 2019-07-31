package com.aptopayments.core.platform

import com.aptopayments.core.UnitTest
import com.aptopayments.core.data.TestDataProvider
import com.aptopayments.core.features.managecard.CardOptions
import com.aptopayments.core.repository.card.usecases.IssueCardUseCase
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.mockito.Mock

class AptoPlatformTest : UnitTest() {
    private val sut = AptoPlatform

    // Collaborators
    @Mock private lateinit var issueCardCardProductUseCase: IssueCardUseCase
    @Mock private lateinit var useCasesWrapper: UseCasesWrapper

    override fun setUp() {
        super.setUp()
        given { useCasesWrapper.issueCardCardProductUseCase }.willReturn(issueCardCardProductUseCase)
        sut.useCasesWrapper = useCasesWrapper
    }

    @Test
    fun `issue card called invoke use case`() {
        // When
        AptoPlatform.issueCard(
                cardProductId = "card_product_id",
                credential = null
        ) {}

        // Then
        verify(issueCardCardProductUseCase).invoke(TestDataProvider.anyObject(), TestDataProvider.anyObject())
    }

    @Test
    fun `balance v1 used set useBalanceV2 to false`() {
        // Given
        AptoPlatform.cardOptions = CardOptions(useBalanceVersionV2 = false)
        val expectedParams = IssueCardUseCase.Params(
                cardProductId = "card_product_id",
                credential = null,
                useBalanceV2 = false
        )

        // When
        AptoPlatform.issueCard(
                cardProductId = "card_product_id",
                credential = null
        ) {}

        // Then
        verify(issueCardCardProductUseCase).invoke(eq(expectedParams), TestDataProvider.anyObject())
    }

    @Test
    fun `balance v2 used set useBalanceV2 to true`() {
        // Given
        AptoPlatform.cardOptions = CardOptions(useBalanceVersionV2 = true)
        val expectedParams = IssueCardUseCase.Params(
                cardProductId = "card_product_id",
                credential = null,
                useBalanceV2 = true
        )

        // When
        AptoPlatform.issueCard(
                cardProductId = "card_product_id",
                credential = null
        ) {}

        // Then
        verify(issueCardCardProductUseCase).invoke(eq(expectedParams), TestDataProvider.anyObject())
    }
}
