package com.aptopayments.mobile.repository.card.remote

import com.aptopayments.mobile.ServiceTest
import com.aptopayments.mobile.repository.card.remote.entities.CardEntity
import com.aptopayments.mobile.repository.card.remote.requests.GetCardRequest
import org.junit.Assert
import org.junit.Test
import org.koin.core.inject
import java.net.HttpURLConnection

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

        Assert.assertEquals(cardEntity, response.body()!!)
    }

    private fun executeGetCard() = sut.getCard(GetCardRequest(accountId)).execute()
}
