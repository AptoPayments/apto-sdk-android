package com.aptopayments.mobile.data.user

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IdDocumentDataPointTest {
    @Test
    fun `given unknown type when fromString then UNKNOWN is returned`() {
        val result = IdDocumentDataPoint.Type.fromString("test")

        assertEquals(IdDocumentDataPoint.Type.UNKNOWN, result)
    }

    @Test
    fun `given SSN type when fromString then SSN is returned`() {
        val result = IdDocumentDataPoint.Type.fromString("ssn")

        assertEquals(IdDocumentDataPoint.Type.SSN, result)
    }
}
