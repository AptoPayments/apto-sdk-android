package com.aptopayments.core.data.transaction

import com.aptopayments.core.UnitTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DeclineCodeTest : UnitTest() {

    @Test
    fun `null decline code converts to null`() {
        // Given
        val declineCode = null

        // When
        val result = DeclineCode.from(declineCode)

        // Then
        assertNull(result)
    }

    @Test
    fun `empty decline code converts to Other`() {
        // Given
        val declineCode = ""

        // When
        val result = DeclineCode.from(declineCode)

        // Then
        assertEquals(result, DeclineCode.Other)
    }

    @Test
    fun `nsf decline code converts to NSF`() {
        // Given
        val declineCode = "decline_nsf"

        // When
        val result = DeclineCode.from(declineCode)

        // Then
        assertEquals(result, DeclineCode.DECLINE_NSF)
    }
}
