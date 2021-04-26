package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.common.ModelDataProvider
import com.aptopayments.mobile.data.card.FeatureStatus
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SetPinEntityTest {

    private lateinit var sut: SetPinEntity

    @Test
    fun `set pin entity converts to set pin`() {
        // Given
        sut = SetPinEntity(status = "enabled")

        // When
        val setPin = sut.toSetPin()

        // Then
        assertEquals(setPin.status, FeatureStatus.ENABLED)
    }

    @Test
    fun `set pin converts to set pin entity`() {
        // When
        val setPinEntity = SetPinEntity.from(ModelDataProvider.setPin(status = FeatureStatus.ENABLED))

        // Then
        assertNotNull(setPinEntity)
        assertEquals(setPinEntity.status, "enabled")
    }

    @Test
    fun `wrong feature status is parsed to disabled`() {
        // Given
        sut = SetPinEntity("UNKNOWN_STATUS")

        // When
        val status = sut.toSetPin().status

        // Then
        assertEquals(FeatureStatus.DISABLED, status)
    }
}
