package com.aptopayments.core.data.user

import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDate

class BirthdateDataPointTest {

    val sut = BirthdateDataPoint(birthdate = LocalDate.of(2020, 4, 14))

    @Test
    fun `when toStringRepresentation then correct format is applied`() {
        assertEquals("April 14, 2020", sut.toStringRepresentation())
    }

    @Test
    fun `when type is requested then Birtdate is returned`() {
        assertEquals(DataPoint.Type.BIRTHDATE, sut.getType())
    }
}
