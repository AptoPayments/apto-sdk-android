package com.aptopayments.mobile.repository.statements.remote.entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class StatementMonthEntityTest {
    @Test
    fun `transformation is correct`() {
        val entity = StatementMonthEntity(10, 2019)
        val statementPeriod = entity.toMonthlyStatePeriod()

        assertEquals(entity.month, statementPeriod.month)
        assertEquals(entity.year, statementPeriod.year)
    }
}
