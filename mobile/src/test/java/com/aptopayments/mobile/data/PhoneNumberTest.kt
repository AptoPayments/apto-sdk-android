package com.aptopayments.mobile.data

import com.aptopayments.mobile.UnitTest
import org.junit.Test
import kotlin.test.assertEquals

class PhoneNumberTest : UnitTest() {
    @Test
    fun `string representation return expected value`() {
        // Given
        val sut = PhoneNumber("1", "2342303796")

        // When
        val stringRepresentation = sut.toStringRepresentation()

        // Then
        assertEquals("+1 2342303796", stringRepresentation)
    }
}
