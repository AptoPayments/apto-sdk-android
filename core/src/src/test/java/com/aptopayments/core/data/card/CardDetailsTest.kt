package com.aptopayments.core.data.card

import org.junit.Assert.*
import org.junit.Test

private const val YEAR_SHORT = "20"
private const val YEAR_LONG = "2020"
private const val MONTH = "03"
private const val PAN = "123"
private const val CVV = "123"
private const val EMPTY = ""

class CardDetailsTest {

    private lateinit var sut: CardDetails

    @Test
    fun `when expirationDate date is empty then empty data returned`() {
        sut = CardDetails(PAN, CVV, EMPTY)

        assertEquals(EMPTY, sut.expirationMonth)
        assertEquals(EMPTY,sut.expirationYear)
    }

    @Test
    fun `when expirationDate date is YY-MM is empty then correct date returned`() {
        sut = CardDetails(PAN, CVV, "$YEAR_SHORT-$MONTH")

        assertEquals(MONTH, sut.expirationMonth)
        assertEquals(YEAR_SHORT, sut.expirationYear)
    }

    @Test
    fun `when expirationDate date is YYYY-MM is empty then correct date returned`() {
        sut = CardDetails(PAN, CVV, "$YEAR_LONG-$MONTH")

        assertEquals(MONTH, sut.expirationMonth)
        assertEquals(YEAR_SHORT, sut.expirationYear)
    }

    @Test
    fun `when expirationDate date is YYYY-MM-DD is empty then correct date returned`() {
        sut = CardDetails(PAN, CVV, "$YEAR_LONG-$MONTH-10")

        assertEquals(MONTH, sut.expirationMonth)
        assertEquals(YEAR_SHORT, sut.expirationYear)
    }
}
