package com.aptopayments.mobile.data.statements

import org.threeten.bp.LocalDate
import java.io.Serializable

data class StatementMonth(val month: Int, val year: Int) : Serializable {

    fun toLocalDate(): LocalDate {
        return LocalDate.of(year, month, 1)
    }
}
