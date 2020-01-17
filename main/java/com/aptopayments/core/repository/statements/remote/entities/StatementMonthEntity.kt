package com.aptopayments.core.repository.statements.remote.entities

import com.aptopayments.core.data.statements.StatementMonth
import com.google.gson.annotations.SerializedName

internal data class StatementMonthEntity(
    @SerializedName("month")
    val month: Int,

    @SerializedName("year")
    val year: Int
) {
    fun toMonthlyStatePeriod() = StatementMonth(month, year)
}
