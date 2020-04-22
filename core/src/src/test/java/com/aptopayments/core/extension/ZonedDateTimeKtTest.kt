package com.aptopayments.core.extension

import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

private const val YEAR = 2020
private const val MONTH = 4
private const val DAY = 9
private const val HOUR = 10

class ZonedDateTimeKtTest {

    @Test
    fun `ZonedDateTime toLocalDateTimeAtTimeZone converts correctly`() {
        val zoneId = ZoneId.of("Europe/London")
        val newZoneId = ZoneId.of("Europe/Madrid")
        val datetime = ZonedDateTime.of(YEAR, MONTH, DAY, HOUR, 0, 0, 0, zoneId)
        val newDateTime = datetime.toLocalDateTimeAtTimeZone(newZoneId)

        assertEquals(HOUR + 1, newDateTime.hour)
    }
}
