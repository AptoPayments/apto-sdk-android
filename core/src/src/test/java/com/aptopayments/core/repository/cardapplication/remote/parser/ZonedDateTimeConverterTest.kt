package com.aptopayments.core.repository.cardapplication.remote.parser

import com.aptopayments.core.network.GsonProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

internal class ZonedDateTimeConverterTest {

    val sut = ZonedDateTimeConverter()

    @Test
    fun `serialized and deserialized ZonedDateTime equals to original`() {
        val originalValue = ZonedDateTime.of(2020, 4, 14, 15, 17, 0, 0, ZoneId.systemDefault())
        val gson = GsonProvider.provide()

        val json = gson.toJson(originalValue)
        val newValue = gson.fromJson(json, ZonedDateTime::class.java)

        assertEquals(originalValue, newValue)
    }
}
