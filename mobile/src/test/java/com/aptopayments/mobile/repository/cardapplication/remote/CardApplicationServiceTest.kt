package com.aptopayments.mobile.repository.cardapplication.remote

import com.aptopayments.mobile.NetworkServiceTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.repository.card.remote.entities.CardEntity
import com.aptopayments.mobile.repository.cardapplication.remote.entities.CardApplicationEntity
import org.junit.Test
import org.koin.test.inject

private const val CARD_APPLICATION_ID = "entity_12345"
private const val WORKFLOW_ID = "entity_222"
private const val ACTION_ID = "entity_111"
private const val CARD_PRODUCT_ID = "entity_1234"
private const val METADATA = "metadata"

internal class CardApplicationServiceTest : NetworkServiceTest() {

    private val design = TestDataProvider.provideIssueCardDesign()

    val sut: CardApplicationService by inject()

    @Test
    fun `when startCardApplication then request is made to the correct url`() {
        enqueueFile("userAccountsApplyResponse200.json")

        sut.startCardApplication(CARD_PRODUCT_ID)

        assertRequestSentTo("v1/user/accounts/apply", "POST")
    }

    @Test
    fun `when startCardApplication then request is made correctly`() {
        enqueueFile("userAccountsApplyResponse200.json")

        sut.startCardApplication(CARD_PRODUCT_ID)

        assertRequestBodyFile("userAccountsApplyRequest.json")
    }

    @Test
    fun `when startCardApplication then response parses correctly`() {
        val fileContent = readFile("issueCardResponse200.json")
        val entity = parseEntity(fileContent, CardApplicationEntity::class.java)
        enqueueContent(fileContent)

        val response = sut.startCardApplication(CARD_PRODUCT_ID)

        response.shouldBeRightAndEqualTo(entity.toCardApplication())
    }

    @Test
    fun `when getCardApplication then request is made to the correct url`() {
        enqueueFile("userAccountsApplicationsStatusResponse200.json")

        sut.getCardApplication(CARD_APPLICATION_ID)

        assertRequestSentTo("v1/user/accounts/applications/$CARD_APPLICATION_ID/status", "GET")
    }

    @Test
    fun `when cancelCardApplication then request is made to the correct url`() {
        enqueueContent("")

        sut.cancelCardApplication(CARD_APPLICATION_ID)

        assertRequestSentTo("v1/user/accounts/applications/$CARD_APPLICATION_ID", "DELETE")
    }

    @Test
    fun `when disclaimersAccept then request is made to the correct url`() {
        enqueueFile("disclaimersAcceptResponse200.json")

        sut.acceptDisclaimer(workflowObjectId = WORKFLOW_ID, actionId = ACTION_ID)

        assertRequestSentTo("v1/disclaimers/accept", "POST")
    }

    @Test
    fun `when disclaimersAccept then request is made correctly`() {
        enqueueFile("disclaimersAcceptResponse200.json")

        sut.acceptDisclaimer(actionId = ACTION_ID, workflowObjectId = WORKFLOW_ID)

        assertRequestBodyFile("disclaimersAcceptRequest.json")
    }

    @Test
    fun `when issuecard then then request is made to the correct url`() {
        enqueueFile("issueCardResponse200.json")

        sut.issueCard(CARD_APPLICATION_ID, null, METADATA, design)

        assertRequestSentTo("v1/user/accounts/issuecard", "POST")
    }

    @Test
    fun `when issuecard then response parses correctly`() {
        val fileContent = readFile("issueCardResponse200.json")
        val cardEntity = parseEntity(fileContent, CardEntity::class.java)
        enqueueContent(fileContent)

        val response = sut.issueCard(CARD_APPLICATION_ID, null, METADATA, design)

        response.shouldBeRightAndEqualTo(cardEntity.toCard())
    }

    @Test
    fun `when issuecard then request is made correctly`() {
        enqueueFile("userAccountsApplyResponse200.json")

        sut.issueCard(CARD_APPLICATION_ID, mapOf("property" to "value"), METADATA, design)

        assertRequestBodyFile("issueCardRequest.json")
    }
}
