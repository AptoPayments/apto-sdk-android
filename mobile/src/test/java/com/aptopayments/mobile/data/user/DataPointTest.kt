package com.aptopayments.mobile.data.user

import org.junit.Assert.*
import org.junit.Test

class DataPointTest {

    @Test
    fun `given unknown type when fromString then null is returned`() {
        val result = DataPoint.Type.fromString("test")

        assertNull(result)
    }

    @Test
    fun `given NAME type when fromString then NAME is returned`() {
        val result = DataPoint.Type.fromString("name")

        assertEquals(DataPoint.Type.NAME, result)
    }
}
