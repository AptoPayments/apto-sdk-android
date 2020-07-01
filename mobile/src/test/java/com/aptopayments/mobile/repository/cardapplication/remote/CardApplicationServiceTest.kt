package com.aptopayments.mobile.repository.cardapplication.remote

import com.aptopayments.mobile.ServiceTest
import com.aptopayments.mobile.repository.card.remote.entities.CardEntity
import com.aptopayments.mobile.repository.cardapplication.remote.entities.CardApplicationEntity
import org.junit.Test
import org.koin.test.inject
import java.net.HttpURLConnection.HTTP_OK
import kotlin.test.assertEquals

private const val CARD_APPLICATION_ID = "entity_12345"
private const val WORKFLOW_ID = "entity_222"
private const val ACTION_ID = "entity_111"
private const val CARD_PRODUCT_ID = "entity_1234"

internal class CardApplicationServiceTest : ServiceTest() {

    val sut: CardApplicationService by inject()

    @Test
    fun `when startCardApplication then request is made to the correct url`() {
        enqueueFile("userAccountsApplyResponse200.json")

        val response = sut.startCardApplication(CARD_PRODUCT_ID).execute()

        assertRequestSentTo("v1/user/accounts/apply", "POST")
        assertCode(response, HTTP_OK)
    }

    @Test
    fun `when startCardApplication then request is made correctly`() {
        enqueueFile("userAccountsApplyResponse200.json")

        sut.startCardApplication(CARD_PRODUCT_ID).execute()

        assertRequestBodyFile("userAccountsApplyRequest.json")
    }

    @Test
    fun `when startCardApplication then response parses correctly`() {
        val fileContent = readFile("issueCardResponse200.json")
        val entity = parseEntity(fileContent, CardApplicationEntity::class.java)
        enqueueContent(fileContent)

        val response = sut.startCardApplication(CARD_PRODUCT_ID).execute()

        assertEquals(entity, response.body()!!)
    }

    @Test
    fun `when getCardApplication then request is made to the correct url`() {
        enqueueFile("userAccountsApplicationsStatusResponse200.json")

        val response = sut.getCardApplication(CARD_APPLICATION_ID).execute()

        assertRequestSentTo("v1/user/accounts/applications/$CARD_APPLICATION_ID/status", "GET")
        assertCode(response, HTTP_OK)
    }

    @Test
    fun `when cancelCardApplication then request is made to the correct url`() {
        enqueueContent("")

        val response = sut.cancelCardApplication(CARD_APPLICATION_ID).execute()

        assertRequestSentTo("v1/user/accounts/applications/$CARD_APPLICATION_ID", "DELETE")
        assertCode(response, HTTP_OK)
    }

    @Test
    fun `when disclaimersAccept then request is made to the correct url`() {
        enqueueFile("disclaimersAcceptResponse200.json")

        val response = sut.acceptDisclaimer(workflowObjectId = WORKFLOW_ID, actionId = ACTION_ID).execute()

        assertRequestSentTo("v1/disclaimers/accept", "POST")
        assertCode(response, HTTP_OK)
    }

    @Test
    fun `when disclaimersAccept then request is made correctly`() {
        enqueueFile("disclaimersAcceptResponse200.json")

        sut.acceptDisclaimer(actionId = ACTION_ID, workflowObjectId = WORKFLOW_ID).execute()

        assertRequestBodyFile("disclaimersAcceptRequest.json")
    }

    @Test
    fun `when issuecard then then request is made to the correct url`() {
        enqueueFile("issueCardResponse200.json")

        val response = sut.issueCard(CARD_APPLICATION_ID, null).execute()

        assertRequestSentTo("v1/user/accounts/issuecard", "POST")
        assertCode(response, HTTP_OK)
    }

    @Test
    fun `when issuecard then response parses correctly`() {
        val fileContent = readFile("issueCardResponse200.json")
        val cardEntity = parseEntity(fileContent, CardEntity::class.java)
        enqueueContent(fileContent)

        val response = sut.issueCard(CARD_APPLICATION_ID, null).execute()

        assertEquals(cardEntity, response.body()!!)
    }
}
