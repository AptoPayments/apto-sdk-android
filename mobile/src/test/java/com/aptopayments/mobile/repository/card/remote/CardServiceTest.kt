package com.aptopayments.mobile.repository.card.remote

import com.aptopayments.mobile.ServiceTest
import com.aptopayments.mobile.repository.card.remote.entities.CardEntity
import com.aptopayments.mobile.repository.card.remote.requests.GetCardRequest
import org.junit.Test
import org.koin.core.inject
import java.net.HttpURLConnection
import kotlin.test.assertEquals

private const val PASSCODE = "1234"
private const val VERIFICATION_ID = "vid_1234"

internal class CardServiceTest : ServiceTest() {

    private val accountId = "crd_1234"

    private val sut: CardService by inject()

    @Test
    fun `when getCard then request is made to the correct url`() {
        enqueueFile("userAccountsResponse200.json")

        val response = executeGetCard()

        assertRequestSentTo("v1/user/accounts/$accountId?show_details=false", "GET")
        assertCode(response, HttpURLConnection.HTTP_OK)
    }

    @Test
    fun `when getCard then response parses correctly`() {
        val fileContent = readFile("userAccountsResponse200.json")
        val cardEntity = parseEntity(fileContent, CardEntity::class.java)
        enqueueContent(fileContent)

        val response = executeGetCard()

        assertEquals(cardEntity, response.body()!!)
    }

    @Test
    fun `when setCardPasscode then request is made to the correct url`() {
        enqueueContent("{}")

        sut.setCardPasscode(accountId, PASSCODE, VERIFICATION_ID).execute()

        assertRequestSentTo("v1/user/accounts/$accountId/passcode", "POST")
    }

    @Test
    fun `when setCardPasscode with verificationID then request is made correctly`() {
        enqueueContent("{}")

        sut.setCardPasscode(accountId, PASSCODE, VERIFICATION_ID).execute()

        assertRequestBodyFile("userAccountsPasscodeRequestWithVerificationPost.json")
    }

    @Test
    fun `when setCardPasscode without verificationID then request is made correctly`() {
        enqueueContent("{}")

        sut.setCardPasscode(accountId, PASSCODE, null).execute()

        assertRequestBodyFile("userAccountsPasscodeRequestPost.json")
    }

    private fun executeGetCard() = sut.getCard(GetCardRequest(accountId)).execute()
}
