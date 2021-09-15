package com.aptopayments.mobile.repository.card.remote

import com.aptopayments.mobile.NetworkServiceTest
import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.data.card.ProvisioningData
import com.aptopayments.mobile.data.card.ProvisioningTokenServiceProvider
import com.aptopayments.mobile.data.card.ProvisioningUserAddress
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.repository.card.remote.entities.ActivatePhysicalCardEntity
import com.aptopayments.mobile.repository.card.remote.entities.CardEntity
import com.aptopayments.mobile.repository.card.remote.requests.GetCardRequest
import com.aptopayments.mobile.repository.fundingsources.remote.entities.BalanceEntity
import org.junit.jupiter.api.Test
import org.koin.core.inject
import kotlin.test.assertTrue

private const val ACCOUNT_ID = "crd_1234"
private const val BALANCE_ID = "bal_1234"
private const val PASSCODE = "1234"
private const val VERIFICATION_ID = "vid_1234"

internal class CardServiceTest : NetworkServiceTest() {

    private val sut: CardService by inject()

    @Test
    fun `when getCard then request is made to the correct url`() {
        enqueueFile("userAccountsResponse200.json")

        sut.getCard(GetCardRequest(ACCOUNT_ID))

        assertRequestSentTo("v1/user/accounts/$ACCOUNT_ID?show_details=false", "GET")
    }

    @Test
    fun `when getCard then response parses correctly`() {
        val fileContent = readFile("userAccountsResponse200.json")
        val cardEntity = parseEntity(fileContent, CardEntity::class.java).toCard()
        enqueueContent(fileContent)

        val response = sut.getCard(GetCardRequest(ACCOUNT_ID))

        response.shouldBeRightAndEqualTo(cardEntity)
    }

    @Test
    fun `when getCards then request is sent to correct url`() {
        enqueueContent("{}")

        sut.getCards()

        assertRequestSentTo("v1/user/accounts", "GET")
    }

    @Test
    fun `when getCards then request is parsed correctly`() {
        val fileContent = readFile("userAccounts200.json")
        enqueueContent(fileContent)

        val response = sut.getCards()

        assertTrue(response is Either.Right)
    }

    @Test
    fun `when lockCard then request is sent to correct url`() {
        enqueueContent("{}")

        sut.lockCard(ACCOUNT_ID)

        assertRequestSentTo("v1/user/accounts/$ACCOUNT_ID/disable", "POST")
    }

    @Test
    fun `when unlockCard then request is sent to correct url`() {
        enqueueContent("{}")

        sut.unlockCard(ACCOUNT_ID)

        assertRequestSentTo("v1/user/accounts/$ACCOUNT_ID/enable", "POST")
    }

    @Test
    fun `when activatePhysicalCard then request is sent to correct url`() {
        enqueueContent("{}")

        sut.activatePhysicalCard(ACCOUNT_ID, "1234")

        assertRequestSentTo("v1/user/accounts/$ACCOUNT_ID/activate_physical", "POST")
    }

    @Test
    fun `given bad code when activatePhysicalCard then request is parsed correctly`() {
        val fileContent = readFile("userAccountsActivatePhysicalError200.json")
        val activateResult =
            parseEntity(fileContent, ActivatePhysicalCardEntity::class.java).toActivatePhysicalCardResult()
        enqueueContent(fileContent)

        val result = sut.activatePhysicalCard(ACCOUNT_ID, "1234")

        result.shouldBeRightAndEqualTo(activateResult)
    }

    @Test
    fun `when getCardBalance then request is sent to correct url`() {
        enqueueContent("{}")

        sut.getCardBalance(ACCOUNT_ID)

        assertRequestSentTo("v1/user/accounts/$ACCOUNT_ID/balance", "GET")
    }

    @Test
    fun `when getCardBalance then response is parsed correctly`() {
        val fileContent = readFile("userAccountsBalance200.json")
        val parsedJson =
            parseEntity(fileContent, BalanceEntity::class.java).toBalance()
        enqueueContent(fileContent)

        val result = sut.getCardBalance(ACCOUNT_ID)

        result.shouldBeRightAndEqualTo(parsedJson)
    }

    @Test
    fun `when setCardBalance then request is sent to correct url`() {
        enqueueContent("{}")

        sut.setCardBalance(ACCOUNT_ID, BALANCE_ID)

        assertRequestSentTo("v1/user/accounts/$ACCOUNT_ID/balance", "POST")
    }

    @Test
    fun `when addCardBalance then request is sent to correct url`() {
        enqueueContent("{}")

        sut.addCardBalance(ACCOUNT_ID, "type_1", "tokenId")

        assertRequestSentTo("v1/user/accounts/$ACCOUNT_ID/balances", "POST")
    }

    @Test
    fun `when setPin then request is sent to correct url`() {
        enqueueContent("{}")

        sut.setPin(ACCOUNT_ID, "1234")

        assertRequestSentTo("v1/user/accounts/$ACCOUNT_ID/pin", "POST")
    }

    @Test
    fun `when setCardPasscode then request is made to the correct url`() {
        enqueueContent("{}")

        sut.setCardPasscode(ACCOUNT_ID, PASSCODE, VERIFICATION_ID)

        assertRequestSentTo("v1/user/accounts/$ACCOUNT_ID/passcode", "POST")
    }

    @Test
    fun `when setCardPasscode with verificationID then request is made correctly`() {
        enqueueContent("{}")

        sut.setCardPasscode(ACCOUNT_ID, PASSCODE, VERIFICATION_ID)

        assertRequestBodyFile("userAccountsPasscodeRequestWithVerificationPost.json")
    }

    @Test
    fun `when setCardPasscode without verificationID then request is made correctly`() {
        enqueueContent("{}")

        sut.setCardPasscode(ACCOUNT_ID, PASSCODE, null)

        assertRequestBodyFile("userAccountsPasscodeRequestPost.json")
    }

    @Test
    fun `when getOrderPhysicalCardConfig then request is made to the correct url`() {
        enqueueContent("{}")

        sut.getOrderPhysicalCardConfig(ACCOUNT_ID)

        assertRequestSentTo("v1/user/accounts/$ACCOUNT_ID/order_physical/config", "GET")
    }

    @Test
    fun `when orderPhysicalCard then request is made to the correct url`() {
        enqueueContent("{}")

        sut.orderPhysicalCard(ACCOUNT_ID)

        assertRequestSentTo("v1/user/accounts/$ACCOUNT_ID/order_physical", "POST")
    }

    @Test
    fun `when getProvisioningData then the request is made to the correct url`() {
        enqueueContent("{}")

        sut.getProvisioningData(ACCOUNT_ID)

        assertRequestSentTo("v1/user/accounts/$ACCOUNT_ID/provision/googlepay", "POST")
    }

    @Test
    fun `when getProvisioningData then response is parsed correctly`() {
        enqueueFile("googlePay200Response200.json")
        val userAddress = ProvisioningUserAddress(
            name = "Matias Example",
            address1 = "Malibu Point 10880",
            address2 = "apartment 1",
            city = "Malibu",
            state = "CA",
            postalCode = "90265",
            country = "US",
            phone = "3202476962"
        )
        val response = ProvisioningData(
            network = Card.CardNetwork.VISA,
            tokenServiceProvider = ProvisioningTokenServiceProvider.VISA,
            displayName = "My card program",
            lastFour = "1234",
            userAddress = userAddress,
            opaquePaymentCard = "opc_123"
        )

        val result = sut.getProvisioningData(ACCOUNT_ID)

        result.shouldBeRightAndEqualTo(response)
    }
}
