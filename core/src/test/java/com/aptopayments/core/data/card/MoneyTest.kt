package com.aptopayments.core.data.card

import com.aptopayments.core.UnitTest
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.test.AfterTest

class MoneyTest : UnitTest() {
    private lateinit var locale: Locale
    @Before
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
        val string = sut.toString()

        // Then
        string `should equal` "$0.00"
    }

    @Test
    fun `given minus zero return zero with two decimal places`() {
        // Given
        val sut = Money("USD", amount = -(0.toDouble()))

        // When
        val string = sut.toString()

        // Then
        string `should equal` "$0.00"
    }

    @Test
    fun `given number larger than zero toString return two decimals`() {
        // Given
        val sut = Money("USD", 12.3012)

        // When
        val string = sut.toString()

        // Then
        string `should equal` "$12.30"
    }

    @Test
    fun `given negative number larger with abs larger than zero toString return two decimals`() {
        // Given
        val sut = Money("USD", -12.3012)

        // When
        val string = sut.toString()

        // Then
        string `should equal` "-$12.30"
    }

    @Test
    fun `given zero something toString return two decimal figures`() {
        // Given
        val sut = Money("USD", 0.003492)

        // When
        val string = sut.toString()

        // Then
        string `should equal` "$0.0035"
    }

    @Test
    fun `given minus zero something toString return two decimal figures`() {
        // Given
        val sut = Money("USD", -0.003492)

        // When
        val string = sut.toString()

        // Then
        string `should equal` "-$0.0035"
    }

    @Test
    fun `given minus number larger than zero toAbsString return two decimals without sign`() {
        // Given
        val sut = Money("USD", -12.3012)

        // When
        val string = sut.toAbsString()

        // Then
        string `should equal` "$12.30"
    }

    @Test
    fun `given minus zero something toAbsString return two decimal figures without sign`() {
        // Given
        val sut = Money("USD", -0.003492)

        // When
        val string = sut.toAbsString()

        // Then
        string `should equal` "$0.0035"
    }

    @Test
    fun `given crypto currency toString return crypto symbol`() {
        // Given
        val sut = Money("BTC", 12.3012)

        // When
        val string = sut.toString()

        // Then
        string `should equal` "BTC12.30"
    }

    @Test
    fun `given crypto currency toAbsString return crypto symbol`() {
        // Given
        val sut = Money("BTC", -12.3012)

        // When
        val string = sut.toAbsString()

        // Then
        string `should equal` "BTC12.30"
    }

    @Test
    fun `given custom currency toString return custom symbol`() {
        // Given
        val sut = Money("MXN", 12.3012)

        // When
        val string = sut.toString()

        // Then
        string `should equal` "MXN12.30"
    }

    @Test
    fun `given custom currency toAbsString return custom symbol`() {
        // Given
        val sut = Money("MXN", -12.3012)

        // When
        val string = sut.toAbsString()

        // Then
        string `should equal` "MXN12.30"
    }

    @Test
    fun `system locale is used`() {
        // Given
        Locale.setDefault(Locale("es", "ES", "MX"))
        val sut = Money("USD", amount = 0.toDouble())

        // When
        val string = sut.toString()

        // Then
        string `should equal` "USD0,00"
    }

    @Test
    fun `no amount toString returns an empty string`() {
        // Given
        val sut = Money("USD", null)

        // When
        val string = sut.toString()

        // Then
        string `should equal` ""
    }

    @Test
    fun `no amount toAbsString returns an empty string`() {
        // Given
        val sut = Money("USD", null)

        // When
        val string = sut.toAbsString()

        // Then
        string `should equal` ""
    }

    @Test
    fun `no currency currencySymbol returns an empty string`() {
        // Given
        val sut = Money(null, 10.0)

        // When
        val currencySymbol = sut.currencySymbol()

        // Then
        currencySymbol `should equal` ""
    }
}
