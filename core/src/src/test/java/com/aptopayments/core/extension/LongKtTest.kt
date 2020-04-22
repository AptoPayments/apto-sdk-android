package com.aptopayments.core.extension

import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.ZoneOffset

private const val MILLIS = 1586429406736L
private const val YEAR = 2020
private const val MONTH = 4
private const val DAY = 9
private const val HOUR = 10

class LongKtTest {

    @Test
    fun `Long is converted correctly to LocalDateTime`() {
        val date = MILLIS.toLocalDateTime(ZoneOffset.UTC)

        assertEquals(YEAR, date.year)
        assertEquals(MONTH, date.monthValue)
        assertEquals(DAY, date.dayOfMonth)
        assertEquals(HOUR, date.hour)
    }

    @Test
    fun `default conversion zone for LocalDateTime is UTC`() {
        val dateUtc = MILLIS.toLocalDateTime(ZoneOffset.UTC)
        val dateWithoutSpecification = MILLIS.toLocalDateTime()

        assertEquals(dateUtc, dateWithoutSpecification)
    }

    @Test
    fun `Long is converted correctly to ZonedDateTime`() {
        val date = MILLIS.toLocalDateTime(ZoneOffset.UTC)

        assertEquals(YEAR, date.year)
        assertEquals(MONTH, date.monthValue)
        assertEquals(DAY, date.dayOfMonth)
        assertEquals(HOUR, date.hour)
    }

    @Test
    fun `default conversion zone for ZonedDateTime is UTC`() {
        val dateUtc = MILLIS.toZonedDateTime(ZoneOffset.UTC)
        val dateWithoutSpecification = MILLIS.toZonedDateTime()

        assertEquals(dateUtc, dateWithoutSpecification)
    }
}
