package com.aptopayments.mobile.repository.p2p.remote

import com.aptopayments.mobile.NetworkServiceTest
import com.aptopayments.mobile.data.PhoneNumber
import com.aptopayments.mobile.data.card.Money
import com.aptopayments.mobile.data.payment.PaymentStatus
import com.aptopayments.mobile.data.transfermoney.CardHolderData
import com.aptopayments.mobile.data.transfermoney.CardHolderName
import com.aptopayments.mobile.data.transfermoney.P2pTransferResponse
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import org.junit.jupiter.api.Nested

import org.junit.jupiter.api.Test
import org.koin.core.inject
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

private const val TEST_EMAIl = "hello@aptopayments.com"
private const val TEST_PHONE_COUNTRY_CODE = "1"
private const val TEST_PHONE_NUMBER = "1234567"
private const val SOURCE_ID = "source_123"
private const val RECIPIENT_ID = "rec_1234"
private const val TRANSFER_ID = "tr_1"

internal class P2pServiceTest : NetworkServiceTest() {

    private val sut: P2pService by inject()

    @Nested
    inner class FindRecipient {
        @Test
        fun `when findRecipient with email then request is made to the correct url`() {
            enqueueContent("")

            sut.findRecipient(email = TEST_EMAIl)

            val encodedEmail = URLEncoder.encode(TEST_EMAIl, UTF_8.toString())
            assertRequestSentTo("v1/p2p/recipient?email=$encodedEmail", "GET")
        }

        @Test
        fun `when findRecipient with phone then request is made to the correct url`() {
            enqueueContent("")
            val phone = PhoneNumber(TEST_PHONE_COUNTRY_CODE, TEST_PHONE_NUMBER)

            sut.findRecipient(phone = phone)

            assertRequestSentTo(
                "v1/p2p/recipient?phone_country_code=$TEST_PHONE_COUNTRY_CODE&phone_number=$TEST_PHONE_NUMBER",
                "GET"
            )
        }

        @Test
        fun `given a response when findRecipient then parsing is correct`() {
            enqueueFile("p2pRecipientsResponse.json")
            val expectedResponse =
                CardHolderData(name = CardHolderName(firstName = "John", lastName = "Smith"), "crdhlr_123456789")

            val response = sut.findRecipient(phone = null, email = TEST_EMAIl)

            response.shouldBeRightAndEqualTo(expectedResponse)
        }
    }

    @Nested
    inner class MakeTransfer {

        private val amount = Money("USD", 10.5)

        @Test
        fun `when makeTransfer is called then request is made to the correct url`() {
            enqueueContent("")

            sut.makeTransfer(sourceId = SOURCE_ID, recipientId = RECIPIENT_ID, amount)

            assertRequestSentTo("v1/p2p/transfer", "POST")
        }

        @Test
        fun `given a response when makeTransfer then parsing is correct`() {
            enqueueFile("p2pTransferResponse.json")
            val expectedResponse =
                P2pTransferResponse(
                    transferId = TRANSFER_ID,
                    status = PaymentStatus.PROCESSED,
                    sourceId = SOURCE_ID,
                    amount = amount,
                    recipientName = CardHolderName(firstName = "John", lastName = "Smith"),
                    createdAt = ZonedDateTime.of(2021, 7, 16, 17, 0, 0, 0, ZoneOffset.UTC)
                )

            val response = sut.makeTransfer(sourceId = SOURCE_ID, recipientId = RECIPIENT_ID, amount)

            response.shouldBeRightAndEqualTo(expectedResponse)
        }

        @Test
        fun `when makeTransfer then request body is correct`() {
            enqueueContent("{}")

            sut.makeTransfer(sourceId = SOURCE_ID, recipientId = RECIPIENT_ID, amount)

            assertRequestBodyFile("p2pTransferRequest.json")
        }
    }
}
