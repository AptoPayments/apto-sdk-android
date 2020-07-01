package com.aptopayments.mobile.data.statements

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertEquals

class MonthlyStatementPeriodTest {

    @Test
    fun `when start year is before end year then period is valid`() {
        val validity = checkSelectedPeriodValidity(1, 2018)
        assertTrue(validity)
    }

    @Test
    fun `when same year and month is before then period is Valid`() {
        val validity = checkSelectedPeriodValidity(1, 2019)
        assertTrue(validity)
    }

    @Test
    fun `when same year and month then period is valid`() {
        val validity = checkSelectedPeriodValidity(10, 2019)
        assertTrue(validity)
    }

    @Test
    fun `when startMonth is older than endMonth then period is NOT valid`() {
        val validity = checkSelectedPeriodValidity(11, 2019)
        assertFalse(validity)
    }

    @Test
    fun `when startYear is older than endYear then period is NOT valid`() {
        val validity = checkSelectedPeriodValidity(1, 2020)
        assertFalse(validity)
    }

    private fun checkSelectedPeriodValidity(startMonth: Int, startYear: Int): Boolean {
        val start = StatementMonth(startMonth, startYear)
        val end = StatementMonth(10, 2019)
        return MonthlyStatementPeriod(start, end).isValid()
    }

    @Test
    fun `when date is not valid then availableMonths is empty`() {
        val start = StatementMonth(11, 2019)
        val endMonth = StatementMonth(10, 2019)

        val period = MonthlyStatementPeriod(start, endMonth)

        assert(period.availableMonths().isEmpty())
    }

    @Test
    fun `when start and end months are equal then availableMonths has one element`() {
        val start = StatementMonth(10, 2019)
        val endMonth = StatementMonth(10, 2019)

        val period = MonthlyStatementPeriod(start, endMonth)

        assertEquals(listOf(StatementMonth(10, 2019)), period.availableMonths())
    }

    @Test
    fun `when many months difference then availableMonths has correct elements`() {
        val start = StatementMonth(2, 2019)
        val endMonth = StatementMonth(10, 2019)

        val period = MonthlyStatementPeriod(start, endMonth)

        val generatedList = (start.month..endMonth.month).map { StatementMonth(it, 2019) }
        assertEquals(generatedList, period.availableMonths())
    }

    @Test
    fun `test included`() {
        val start = StatementMonth(9, 2019)
        val end = StatementMonth(11, 2019)
        val period = MonthlyStatementPeriod(start, end)
        val testedElements = listOf(Pair(8, false), Pair(9, true), Pair(10, true), Pair(11, true), Pair(12, false))
        testedElements.forEach {
            assertEquals(period.contains(it.first, 2019), it.second)
        }
    }
}
