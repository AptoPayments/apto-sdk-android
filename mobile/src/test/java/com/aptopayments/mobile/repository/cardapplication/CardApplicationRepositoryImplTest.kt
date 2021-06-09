package com.aptopayments.mobile.repository.cardapplication

import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.data.card.CardApplication
import com.aptopayments.mobile.data.card.IssueCardDesign
import com.aptopayments.mobile.data.card.SelectBalanceStoreResult
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.repository.cardapplication.remote.CardApplicationService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test

private const val CARD_APPLICATION_ID = "entity_12345"
private const val WORKFLOW_ID = "entity_222"
private const val ACTION_ID = "entity_111"
private const val CARD_PRODUCT_ID = "entity_1234"
private const val METADATA = "metadata"
private const val TOKEN_ID = "Token_id"

class CardApplicationRepositoryImplTest {
    private val service: CardApplicationService = mock()

    private val sut = CardApplicationRepositoryImpl(service)

    @Test
    fun `when startCardApplication then service called correctly`() {
        val cardApplication = mock<CardApplication>()
        whenever(service.startCardApplication(CARD_PRODUCT_ID)).thenReturn(cardApplication.right())

        val result = sut.startCardApplication(CARD_PRODUCT_ID)

        result.shouldBeRightAndEqualTo(cardApplication)
        verify(service).startCardApplication(cardProductId = CARD_PRODUCT_ID)
    }

    @Test
    fun `when getCardApplication then service called correctly`() {
        val cardApplication = mock<CardApplication>()
        whenever(service.getCardApplication(CARD_APPLICATION_ID)).thenReturn(cardApplication.right())

        val result = sut.getCardApplication(CARD_APPLICATION_ID)

        result.shouldBeRightAndEqualTo(cardApplication)
        verify(service).getCardApplication(cardApplicationId = CARD_APPLICATION_ID)
    }

    @Test
    fun `when cancelCardApplication then service called correctly`() {
        whenever(service.cancelCardApplication(CARD_APPLICATION_ID)).thenReturn(Unit.right())

        val result = sut.cancelCardApplication(CARD_APPLICATION_ID)

        result.shouldBeRightAndEqualTo(Unit)
        verify(service).cancelCardApplication(cardApplicationId = CARD_APPLICATION_ID)
    }

    @Test
    fun `when setBalanceStore then service called correctly`() {
        val balanceResult = mock<SelectBalanceStoreResult>()
        whenever(service.setBalanceStore(CARD_APPLICATION_ID, TOKEN_ID)).thenReturn(balanceResult.right())

        val result = sut.setBalanceStore(CARD_APPLICATION_ID, TOKEN_ID)

        result.shouldBeRightAndEqualTo(balanceResult)
        verify(service).setBalanceStore(cardApplicationId = CARD_APPLICATION_ID, tokenId = TOKEN_ID)
    }

    @Test
    fun `when acceptDisclaimer then service called correctly`() {
        whenever(sut.acceptDisclaimer(WORKFLOW_ID, ACTION_ID)).thenReturn(Unit.right())

        val result = sut.acceptDisclaimer(WORKFLOW_ID, ACTION_ID)

        result.shouldBeRightAndEqualTo(Unit)
        verify(service).acceptDisclaimer(workflowObjectId = WORKFLOW_ID, actionId = ACTION_ID)
    }

    @Test
    fun `when issueCard then service called correctly`() {
        val card = mock<Card>()
        val design: IssueCardDesign = mock()
        whenever(service.issueCard(CARD_APPLICATION_ID, METADATA, design)).thenReturn(card.right())
        val result = sut.issueCard(CARD_APPLICATION_ID, METADATA, design)

        result.shouldBeRightAndEqualTo(card)
        verify(service).issueCard(
            applicationId = CARD_APPLICATION_ID,
            metadata = METADATA,
            design = design
        )
    }
}
