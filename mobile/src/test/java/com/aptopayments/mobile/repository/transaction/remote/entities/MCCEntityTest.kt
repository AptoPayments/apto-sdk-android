package com.aptopayments.mobile.repository.transaction.remote.entities

import com.aptopayments.mobile.data.transaction.MCC

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

private const val NAME = "test"

class MCCEntityTest {

    @Test
    fun `given unknown MCC icon when toMCC then Other is parsed`() {
        val sut = MCCEntity(name = NAME, icon = "help")

        val result = sut.toMCC()

        assertEquals(MCC(NAME, MCC.Icon.OTHER), result)
    }

    @Test
    fun `given camera MCC icon when toMCC then Camera is parsed`() {
        val sut = MCCEntity(name = NAME, icon = "camera")

        val result = sut.toMCC()

        assertEquals(MCC(NAME, MCC.Icon.CAMERA), result)
    }

    @Test
    fun `given null MCC icon when toMCC then null is parsed`() {
        val sut = MCCEntity(name = NAME, icon = null)

        val result = sut.toMCC()

        assertEquals(MCC(NAME, null), result)
    }
}
