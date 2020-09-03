package com.aptopayments.mobile.platform

import com.aptopayments.mobile.UnitTest

import org.junit.Test
import kotlin.test.assertEquals

class AptoSdkEnvironmentTest : UnitTest() {

    @Test
    fun `when parsing prd then PRD enum is returned`() {
        val environment = AptoSdkEnvironment.fromString("prd")

        assertEquals(AptoSdkEnvironment.PRD, environment)
    }

    @Test
    fun `when parsing sbx then SBX enum is returned`() {
        val environment = AptoSdkEnvironment.fromString("sbx")

        assertEquals(AptoSdkEnvironment.SBX, environment)
    }

    @Test
    fun `when parsing stg then STG enum is returned`() {
        val environment = AptoSdkEnvironment.fromString("stg")

        assertEquals(AptoSdkEnvironment.STG, environment)
    }
}
