package com.aptopayments.mobile.data.payment

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PaymentStatusTest {
    @Test
    fun `given unknown type when fromString then UNKNOWN is returned`() {
        val result = PaymentStatus.fromString("test")

        assertEquals(PaymentStatus.FAILED, result)
    }

    @Test
    fun `given pending type when fromString then PENDING is returned`() {
        val result = PaymentStatus.fromString("pending")

        assertEquals(PaymentStatus.PENDING, result)
    }
}
