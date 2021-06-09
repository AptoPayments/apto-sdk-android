package com.aptopayments.mobile.data.card

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

internal class CurrencySymbolProviderTest {
    val sut = CurrencySymbolProvider()

    @Test
    fun `given empty symbol then empty is provided`() {
        val result = sut.provide("")

        assertEquals("", result)
    }

    @Test
    fun `given null symbol then empty is provided`() {
        val result = sut.provide(null)

        assertEquals("", result)
    }

    @Test
    fun `given EUR then correct symbol is provided`() {
        val result = sut.provide("EUR")

        assertEquals("€", result)
    }

    @Test
    fun `given MX locale and USD then correct symbol is provided`() {
        Locale.setDefault(Locale("es", "ES", "MX"))

        val result = sut.provide("USD")

        assertEquals("USD", result)
    }

    @Test
    fun `given USA Spanish locale and USD then correct symbol is provided`() {
        Locale.setDefault(Locale("EN", "US"))

        val result = sut.provide("USD")

        assertEquals("\$", result)
    }

    @Test
    fun `given USA English locale and USD then correct symbol is provided`() {
        Locale.setDefault(Locale("ES", "US"))

        val result = sut.provide("USD")

        assertEquals("\$", result)
    }

    @Test
    fun `given GBP then correct symbol is provided`() {
        val result = sut.provide("GBP")

        assertEquals("£", result)
    }

    @Test
    fun `given TEST then correct symbol is provided`() {
        val result = sut.provide("TEST")

        assertEquals("TEST", result)
    }

    @Test
    fun `given CHINA locale and CNY then correct symbol is provided`() {
        Locale.setDefault(Locale.CHINA)

        val result = sut.provide("CNY")

        assertEquals("￥", result)
    }

    @Test
    fun `given USA locale and CNY then correct symbol is provided`() {
        Locale.setDefault(Locale.US)

        val result = sut.provide("CNY")

        assertEquals("CNY", result)
    }
}
