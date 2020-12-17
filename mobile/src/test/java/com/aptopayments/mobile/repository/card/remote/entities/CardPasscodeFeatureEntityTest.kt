package com.aptopayments.mobile.repository.card.remote.entities

import junit.framework.Assert.assertFalse
import org.junit.Test
import kotlin.test.assertTrue

internal class CardPasscodeFeatureEntityTest {

    @Test
    fun `whenever all parameters are null then object is created correctly`() {
        val entity = CardPasscodeFeatureEntity()

        val result = entity.toCardPasscode()

        assertFalse(result.isEnabled)
        assertFalse(result.isPasscodeSet)
        assertTrue(result.isVerificationRequired)
    }

    @Test
    fun `whenever status is disabled then object is created correctly`() {
        val entity = CardPasscodeFeatureEntity("disabled")

        val result = entity.toCardPasscode()

        assertFalse(result.isEnabled)
    }

    @Test
    fun `whenever status is enabled then object is created correctly`() {
        val entity = CardPasscodeFeatureEntity("enabled")

        val result = entity.toCardPasscode()

        assertTrue(result.isEnabled)
    }

    @Test
    fun `whenever passcode is not set then object is created correctly`() {
        val entity = CardPasscodeFeatureEntity(passcodeSet = false)

        val result = entity.toCardPasscode()

        assertFalse(result.isPasscodeSet)
    }

    @Test
    fun `whenever passcode is set then object is created correctly`() {
        val entity = CardPasscodeFeatureEntity(passcodeSet = true)

        val result = entity.toCardPasscode()

        assertTrue(result.isPasscodeSet)
    }

    @Test
    fun `whenever verificationRequired  then object is created correctly`() {
        val entity = CardPasscodeFeatureEntity(verificationRequired = false)

        val result = entity.toCardPasscode()

        assertFalse(result.isVerificationRequired)
    }

    @Test
    fun `whenever verificationRequired is false then object is created correctly`() {
        val entity = CardPasscodeFeatureEntity(verificationRequired = true)

        val result = entity.toCardPasscode()

        assertTrue(result.isVerificationRequired)
    }
}
