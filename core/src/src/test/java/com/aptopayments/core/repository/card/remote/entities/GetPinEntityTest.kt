package com.aptopayments.core.repository.card.remote.entities

import com.aptopayments.core.UnitTest
import com.aptopayments.core.common.ModelDataProvider
import com.aptopayments.core.data.card.FeatureStatus
import com.aptopayments.core.data.card.FeatureType
import org.junit.Test
import org.mockito.Mock
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class GetPinEntityTest : UnitTest() {

    @Mock
    private lateinit var mockPhoneNumberEntity: PhoneNumberEntity

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
        val getPinEntity = GetPinEntity.from(ModelDataProvider.getPin(
                status = FeatureStatus.ENABLED,
                type = FeatureType.Voip())
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