package com.aptopayments.mobile.repository.card.remote.entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

private const val CURRENCY = "USD"
class FundingSingleLimitEntityTest {

    @Test
    fun `toFundingSingleLimit converts correctly`() {
        val maxLimit = 10.0
        val sut = FundingSingleLimitEntity(MoneyEntity(CURRENCY, maxLimit))

        val transformed = sut.toFundingSingleLimit()

        assertEquals(CURRENCY, transformed.max.currency)
        assertEquals(maxLimit, transformed.max.amount)
    }
}
