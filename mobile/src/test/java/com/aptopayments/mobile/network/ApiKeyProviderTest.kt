package com.aptopayments.mobile.network

import com.aptopayments.mobile.platform.AptoSdkEnvironment
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

private const val API_KEY = "1234"

class ApiKeyProviderTest {

    val sut = ApiKeyProvider

    @Test
    fun `when apikey set then get is correct`() {
        sut.set(API_KEY, AptoSdkEnvironment.PRD)

        assertEquals(API_KEY, sut.apiKey)
    }

    @Test
    fun `when environment set to PRD then get is correct`() {
        sut.set(API_KEY, AptoSdkEnvironment.PRD)

        assertEquals(AptoSdkEnvironment.PRD, sut.environment)
    }

    @Test
    fun `when environment set to SBX then get is correct`() {
        sut.set(API_KEY, AptoSdkEnvironment.SBX)

        assertEquals(AptoSdkEnvironment.SBX, sut.environment)
    }

    @Test
    fun `when environment set to PRD then isCurrentEnvironmentPrd is true`() {
        sut.set(API_KEY, AptoSdkEnvironment.PRD)

        assertTrue(sut.isCurrentEnvironmentPrd())
    }

    @Test
    fun `when environment set to SBX then isCurrentEnvironmentPrd is false`() {
        sut.set(API_KEY, AptoSdkEnvironment.SBX)

        assertFalse(sut.isCurrentEnvironmentPrd())
    }
}
