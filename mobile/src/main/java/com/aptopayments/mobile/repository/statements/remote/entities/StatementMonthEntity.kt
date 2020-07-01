package com.aptopayments.mobile.repository.statements.remote.entities

import com.aptopayments.mobile.data.statements.StatementMonth
import com.google.gson.annotations.SerializedName

internal data class StatementMonthEntity(
    @SerializedName("month")
    val month: Int,

    @SerializedName("year")
    val year: Int
) {
    fun toMonthlyStatePeriod() = StatementMonth(month, year)
}
