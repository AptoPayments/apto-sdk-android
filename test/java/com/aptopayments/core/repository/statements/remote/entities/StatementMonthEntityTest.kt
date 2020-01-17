package com.aptopayments.core.repository.statements.remote.entities

import org.junit.Assert.*
import org.junit.Test

class StatementMonthEntityTest{
    @Test
    fun `transformation is correct`(){
        val entity = StatementMonthEntity(10,2019)
        val statementPeriod = entity.toMonthlyStatePeriod()

        assertEquals(entity.month, statementPeriod.month)
        assertEquals(entity.year, statementPeriod.year)
    }
}
