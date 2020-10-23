package com.aptopayments.mobile.repository.paymentsources

import com.aptopayments.mobile.NetworkServiceTest
import com.aptopayments.mobile.data.paymentsources.NewCard
import org.junit.Test
import org.koin.core.inject
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import kotlin.test.assertTrue

internal class PaymentSourcesServiceTest : NetworkServiceTest() {

    override var environments = listOf(Environment.VAULT)

    private val sut: PaymentSourcesService by inject()

    @Test
    fun `when addPaymentSource then request is made to the correct url`() {
        enqueueFile("addPaymentSourceResponse400.json", HTTP_BAD_REQUEST)
        val response = sut.addPaymentSource(getNewCard())

        assertRequestSentTo("v1/payment_sources", "POST")
        assertTrue(response.isLeft)
    }

    @Test
    fun `when addPaymentSource then correct request is sent`() {
        enqueueFile("addPaymentSourceResponse400.json", HTTP_BAD_REQUEST)
        val response = sut.addPaymentSource(getNewCard())

        assertRequestBodyFile("addPaymentSourceRequest.json")
    }

    private fun getNewCard(): NewCard {
        return NewCard(
            description = "test description",
            pan = "4242424242424242",
            cvv = "123",
            expirationMonth = "12",
            expirationYear = "22",
            zipCode = "12345"
        )
    }
}
