package com.aptopayments.core.repository.cardapplication.remote.parser

import com.aptopayments.core.network.ApiCatalog
import org.junit.Assert.*
import org.junit.Test
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

class ZonedDateTimeConverterTest {

    val sut = ZonedDateTimeConverter()

    @Test
    fun `serialized and deserialized ZonedDateTime equals to original`() {
        val originalValue = ZonedDateTime.of(2020, 4, 14, 15, 17, 0, 0, ZoneId.systemDefault())

        val json = ApiCatalog.gson().toJson(originalValue)
        val newValue = ApiCatalog.gson().fromJson(json, ZonedDateTime::class.java)

        assertEquals(originalValue, newValue)
    }
}
