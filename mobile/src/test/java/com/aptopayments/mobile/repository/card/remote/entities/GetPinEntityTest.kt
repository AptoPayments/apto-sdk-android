package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.common.ModelDataProvider
import com.aptopayments.mobile.data.card.FeatureStatus
import com.aptopayments.mobile.data.card.FeatureType
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class GetPinEntityTest {

    private val mockPhoneNumberEntity: PhoneNumberEntity = mock()

    private lateinit var sut: GetPinEntity

    @Test
    fun `get pin entity converts to get pin`() {
        // Given
        sut = GetPinEntity(
            status = "enabled",
            type = "voip",
            ivrPhoneEntity = mockPhoneNumberEntity
        )

        // When
        val getPin = sut.toGetPin()

        // Then
        assertEquals(getPin.status, FeatureStatus.ENABLED)
        assertEquals(getPin.type, FeatureType.Voip())
    }

    @Test
    fun `get pin converts to get pin entity`() {
        // When
        val getPinEntity = GetPinEntity.from(
            ModelDataProvider.getPin(
                status = FeatureStatus.ENABLED,
                type = FeatureType.Voip()
            )
        )

        // Then
        assertNotNull(getPinEntity)
        assertEquals(getPinEntity.status, "enabled")
        assertEquals(getPinEntity.type, "voip")
        assertNull(getPinEntity.ivrPhoneEntity)
    }

    @Test
    fun `ivr phone is stored when converting to entity`() {
        // Given
        val phone = ModelDataProvider.phone()

        // When
        val getPinEntity = GetPinEntity.from(ModelDataProvider.getPin(type = FeatureType.Ivr(phone)))

        // Then
        assertNotNull(getPinEntity)
        assertEquals(getPinEntity.type, "ivr")
        assertNotNull(getPinEntity.ivrPhoneEntity)
        assertEquals(getPinEntity.ivrPhoneEntity.countryCode, phone.countryCode)
        assertEquals(getPinEntity.ivrPhoneEntity.phoneNumber, phone.phoneNumber)
    }
}
