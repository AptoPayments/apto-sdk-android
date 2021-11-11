package com.aptopayments.mobile.data.card

import com.aptopayments.mobile.repository.card.remote.entities.GenericFeatureEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GenericFeatureTest {

    @Test
    fun `given empty entity when transformed then status is disabled`() {
        val entity = GenericFeatureEntity()

        val sut = entity.toFeature()

        assertFalse(sut.isEnabled)
    }

    @Test
    fun `given entity with disabled status when transformed then status is disabled`() {
        val entity = GenericFeatureEntity(status = "disabled")

        val sut = entity.toFeature()

        assertFalse(sut.isEnabled)
    }

    @Test
    fun `given entity with enabled status when transformed then status is enabled`() {
        val entity = GenericFeatureEntity(status = "enabled")

        val sut = entity.toFeature()

        assertTrue(sut.isEnabled)
    }
}
