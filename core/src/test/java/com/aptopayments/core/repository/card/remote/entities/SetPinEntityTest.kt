package com.aptopayments.core.repository.card.remote.entities

import com.aptopayments.core.UnitTest
import com.aptopayments.core.common.ModelDataProvider
import com.aptopayments.core.data.card.FeatureStatus
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SetPinEntityTest : UnitTest() {

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
        sut = SetPinEntity()

        // When
        val status = sut.parseFeatureStatus("UNKNOWN_STATUS")

        // Then
        assertNotNull(status)
        assertEquals(status, FeatureStatus.DISABLED)
    }
}
