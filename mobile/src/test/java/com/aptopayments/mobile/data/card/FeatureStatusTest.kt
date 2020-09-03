package com.aptopayments.mobile.data.card

import org.junit.Assert.assertEquals
import org.junit.Test

class FeatureStatusTest {
    @Test
    fun `enabled parses correctly`() {
        val value = FeatureStatus.fromString("enabled")

        assertEquals(FeatureStatus.ENABLED, value)
    }

    @Test
    fun `disabled parses correctly`() {
        val value = FeatureStatus.fromString("disabled")

        assertEquals(FeatureStatus.DISABLED, value)
    }
}
