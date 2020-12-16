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

    @Test
    fun `fromBoolean with true turns into Enabled`() {
        val value = FeatureStatus.fromBoolean(true)

        assertEquals(FeatureStatus.ENABLED, value)
    }

    @Test
    fun `fromBoolean with true turns into Disabled`() {
        val value = FeatureStatus.fromBoolean(false)

        assertEquals(FeatureStatus.DISABLED, value)
    }

    @Test
    fun `toBoolean with Enabled turns into true`() {
        assertEquals(true, FeatureStatus.ENABLED.toBoolean())
    }

    @Test
    fun `toBoolean with Disabled turns into false`() {
        assertEquals(false, FeatureStatus.DISABLED.toBoolean())
    }
}
