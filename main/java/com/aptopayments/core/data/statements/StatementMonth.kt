package com.aptopayments.core.data.statements

import org.threeten.bp.LocalDate

data class StatementMonth(val month: Int, val year: Int) {

    fun toLocalDate(): LocalDate {
        return LocalDate.of(year, month, 1)
    }
}
