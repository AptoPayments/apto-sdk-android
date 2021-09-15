package com.aptopayments.mobile.data.card

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Locale
import kotlin.test.AfterTest
import kotlin.test.assertEquals

private const val VALUE_0 = "$0.00"

class MoneyTest {

    private lateinit var locale: Locale

    @BeforeEach
    fun setup() {
        locale = Locale.getDefault()
        Locale.setDefault(Locale("en", "US"))
    }

    @AfterTest
    fun tearDown() {
        Locale.setDefault(locale)
    }

    @Test
    fun `given zero return zero with two decimal places`() {
        // Given
        val sut = Money("USD", amount = 0.toDouble())

        // When
        val value = sut.toString()

        // Then
        assertEquals(VALUE_0, value)
    }

    @Test
    fun `given minus zero return zero with two decimal places`() {
        // Given
        val sut = Money("USD", amount = -(0.toDouble()))

        // When
        val value = sut.toString()

        // Then
        assertEquals(VALUE_0, value)
    }

    @Test
    fun `given number larger than zero toString return two decimals`() {
        // Given
        val sut = Money("USD", 12.3012)

        // When
        val value = sut.toString()

        // Then
        assertEquals("$12.30", value)
    }

    @Test
    fun `given negative number larger with abs larger than zero toString return two decimals`() {
        // Given
        val sut = Money("USD", -12.3012)

        // When
        val value = sut.toString()

        // Then
        assertEquals("-$12.30", value)
    }

    @Test
    fun `given zero something toString return two decimal figures`() {
        // Given
        val sut = Money("USD", 0.003492)

        // When
        val value = sut.toString()

        // Then
        assertEquals("$0.0035", value)
    }

    @Test
    fun `given minus zero something toString return two decimal figures`() {
        // Given
        val sut = Money("USD", -0.003492)

        // When
        val value = sut.toString()

        // Then
        assertEquals("-$0.0035", value)
    }

    @Test
    fun `given minus number larger than zero toAbsString return two decimals without sign`() {
        // Given
        val sut = Money("USD", -12.3012)

        // When
        val value = sut.toAbsString()

        // Then
        assertEquals("$12.30", value)
    }

    @Test
    fun `given minus zero something toAbsString return two decimal figures without sign`() {
        // Given
        val sut = Money("USD", -0.003492)

        // When
        val value = sut.toAbsString()

        // Then
        assertEquals("$0.0035", value)
    }

    @Test
    fun `given crypto currency toString return crypto symbol`() {
        // Given
        val sut = Money("BTC", 12.3012)

        // When
        val value = sut.toString()

        // Then
        assertEquals("BTC12.30", value)
    }

    @Test
    fun `given crypto currency toAbsString return crypto symbol`() {
        // Given
        val sut = Money("BTC", -12.3012)

        // When
        val value = sut.toAbsString()

        // Then
        assertEquals("BTC12.30", value)
    }

    @Test
    fun `given custom currency toString return custom symbol`() {
        // Given
        val sut = Money("MXN", 12.3012)

        // When
        val value = sut.toString()

        // Then
        assertEquals("MX$12.30", value)
    }

    @Test
    fun `given custom currency toAbsString return custom symbol`() {
        // Given
        val sut = Money("MXN", -12.3012)

        // When
        val value = sut.toAbsString()

        // Then
        assertEquals("MX$12.30", value)
    }

    @Test
    fun `system locale is used`() {
        // Given
        Locale.setDefault(Locale("es", "ES", "MX"))
        val sut = Money("USD", amount = 0.toDouble())

        // When
        val value = sut.toString()

        // Then
        assertEquals("US$0,00", value)
    }

    @Test
    fun `no amount toString returns an empty string`() {
        // Given
        val sut = Money("USD", null)

        // When
        val value = sut.toString()

        // Then
        assertEquals("", value)
    }

    @Test
    fun `no amount toAbsString returns an empty string`() {
        // Given
        val sut = Money("USD", null)

        // When
        val value = sut.toAbsString()

        // Then
        assertEquals("", value)
    }

    @Test
    fun `no currency currencySymbol returns an empty string`() {
        // Given
        val sut = Money(null, 10.0)

        // When
        val currencySymbol = sut.currencySymbol()

        // Then
        assertEquals("", currencySymbol)
    }
}
