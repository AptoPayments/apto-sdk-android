package com.aptopayments.mobile.data.statements

import org.threeten.bp.LocalDate

data class MonthlyStatementPeriod(val start: StatementMonth, val end: StatementMonth) {

    fun isValid(): Boolean {
        return (start.year < end.year) || (start.year == end.year && start.month <= end.month)
    }

    fun availableMonths(): List<StatementMonth> {
        val months = mutableListOf<StatementMonth>()
        if (isValid()) {
            var date = start.toLocalDate()
            val endDate = end.toLocalDate().plusDays(1)

            while (endDate.isAfter(date)) {
                months.add(StatementMonth(date.monthValue, date.year))
                date = date.plusMonths(1)
            }
        }
        return months
    }

    fun contains(month: Int, year: Int): Boolean {
        val start = LocalDate.of(start.year, start.month, 1)
        val end = LocalDate.of(end.year, end.month, 28)
        val tested = LocalDate.of(year, month, 15)

        return start.isBefore(tested) && tested.isBefore(end)
    }
}
