package com.aptopayments.mobile.data.user

import org.junit.Assert.*
import org.junit.Test

class VerificationStatusTest {

    @Test
    fun `given empty string when from() then FAILED is returned`() {
        val result = VerificationStatus.from("")

        assertEquals(VerificationStatus.FAILED, result)
    }

    @Test
    fun `given unrecognized string when from() then FAILED is returned`() {
        val result = VerificationStatus.from("hello")

        assertEquals(VerificationStatus.FAILED, result)
    }

    @Test
    fun `given pending string when from() then PENDING is returned`() {
        val result = VerificationStatus.from("pending")

        assertEquals(VerificationStatus.PENDING, result)
    }

    @Test
    fun `given passed string when from() then PASSED is returned`() {
        val result = VerificationStatus.from("passed")

        assertEquals(VerificationStatus.PASSED, result)
    }

    @Test
    fun `given failed string when from() then FAILED is returned`() {
        val result = VerificationStatus.from("failed")

        assertEquals(VerificationStatus.FAILED, result)
    }
}
