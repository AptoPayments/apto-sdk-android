package com.aptopayments.mobile.data.user

import org.junit.Test
import kotlin.test.assertEquals

class AddressDataPointTest {

    @Test
    fun `Address with all the components is well represented`() {

        // Given
        val sut = AddressDataPoint(
            streetOne = "streetOne",
            streetTwo = "streetTwo",
            locality = "locality",
            region = "region",
            postalCode = "postalCode",
            country = "country"
        )

        // When
        val result = sut.toStringRepresentation()

        // Then
        assertEquals(result, "streetOne, streetTwo, locality, region, postalCode, country")
    }

    @Test
    fun `Address without all the components is well represented`() {

        // Given
        val sut = AddressDataPoint(
            streetOne = "streetOne",
            streetTwo = "streetTwo",
            locality = "locality",
            postalCode = "postalCode",
            country = "country"
        )

        // When
        val result = sut.toStringRepresentation()

        // Then
        assertEquals(result, "streetOne, streetTwo, locality, postalCode, country")
    }
}
