package com.aptopayments.mobile.repository.statements.remote.entities

import com.aptopayments.mobile.data.statements.MonthlyStatementPeriod
import com.google.gson.annotations.SerializedName

internal data class MonthlyStatementPeriodEntity(
    @SerializedName("start")
    val start: StatementMonthEntity,

    @SerializedName("end")
    val end: StatementMonthEntity
) {
    fun toMonthlyStatementPeriod(): MonthlyStatementPeriod {
        val newStart = start.toMonthlyStatePeriod()
        val newEnd = end.toMonthlyStatePeriod()

        return MonthlyStatementPeriod(newStart, newEnd)
    }
}
