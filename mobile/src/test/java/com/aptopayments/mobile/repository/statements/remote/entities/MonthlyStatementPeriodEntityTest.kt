package com.aptopayments.mobile.repository.statements.remote.entities

import org.junit.Assert.assertEquals
import org.junit.Test

class MonthlyStatementPeriodEntityTest {

    @Test
    fun `transformation is done correctly`() {
        val start = StatementMonthEntity(2, 2019)
        val end = StatementMonthEntity(10, 2019)

        val entity = MonthlyStatementPeriodEntity(start, end)
        val transformed = entity.toMonthlyStatementPeriod()

        assertEquals(start.month, transformed.start.month)
        assertEquals(start.year, transformed.start.year)
        assertEquals(end.month, transformed.end.month)
        assertEquals(end.year, transformed.end.year)
    }
}
