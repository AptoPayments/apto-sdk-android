package com.aptopayments.mobile.data

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PhoneNumberTest {
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
