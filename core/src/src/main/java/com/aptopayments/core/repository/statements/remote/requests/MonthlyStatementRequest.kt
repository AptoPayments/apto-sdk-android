package com.aptopayments.core.repository.statements.remote.requests

import com.google.gson.annotations.SerializedName

internal data class MonthlyStatementRequest(

    @SerializedName("month")
    val month: Int,

    @SerializedName("year")
    val year: Int
)
