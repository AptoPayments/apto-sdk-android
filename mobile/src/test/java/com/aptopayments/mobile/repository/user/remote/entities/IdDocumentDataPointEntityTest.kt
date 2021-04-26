package com.aptopayments.mobile.repository.user.remote.entities

import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.data.user.IdDocumentDataPoint
import com.google.gson.Gson
import org.junit.Test
import kotlin.test.assertEquals

class IdDocumentDataPointEntityTest {
    @Test
    fun `converts to id document data point`() {
        // Given
        val sut = IdDocumentDataPointEntity(
            verified = true,
            type = "SSN",
            value = "000000000",
            country = "US"
        )

        // When
        val dataPoint = sut.toDataPoint()

        // Then
        assertEquals(true, dataPoint.verified)
        assertEquals(IdDocumentDataPoint.Type.SSN, dataPoint.type)
        assertEquals("000000000", dataPoint.value)
        assertEquals("US", dataPoint.country)
    }

    @Test
    fun `from create entity from data point`() {
        // Given
        val dataPoint = IdDocumentDataPoint(
            verified = true,
            type = IdDocumentDataPoint.Type.SSN,
            value = "000000000",
            country = "US"
        )

        // When
        val entity = IdDocumentDataPointEntity.from(dataPoint)

        // Then
        assertEquals(true, entity.verified)
        assertEquals("SSN", entity.type)
        assertEquals("000000000", entity.value)
        assertEquals("US", entity.country)
    }

    @Test
    fun `IdDocumentParsedCorrectly from json`() {
        val json = TestDataProvider.provideCorrectIdDocumentDataPointEntity()

        val sut = Gson().fromJson(json, IdDocumentDataPointEntity::class.java).toDataPoint()

        assertEquals(sut.type, IdDocumentDataPoint.Type.SSN)
        assertEquals(sut.value, "111119999")
        assertEquals(sut.verified, false)
        assertEquals(sut.country, "US")
        assertEquals(sut.notSpecified, false)
    }
}
