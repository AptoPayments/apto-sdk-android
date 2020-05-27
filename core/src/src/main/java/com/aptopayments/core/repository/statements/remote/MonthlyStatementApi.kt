package com.aptopayments.core.repository.statements.remote

import com.aptopayments.core.repository.statements.remote.entities.MonthlyStatementPeriodEntity
import com.aptopayments.core.repository.statements.remote.entities.MonthlyStatementReportEntity
import com.aptopayments.core.repository.statements.remote.requests.MonthlyStatementRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private const val MONTHLY_STATEMENT_PATH = "v1/user/statements"
private const val MONTHLY_STATEMENT_PERIOD_PATH = "v1/user/statements/period"

internal interface MonthlyStatementApi {

    @POST(MONTHLY_STATEMENT_PATH)
    fun getMonthlyStatement(@Body request: MonthlyStatementRequest): Call<MonthlyStatementReportEntity>

    @GET(MONTHLY_STATEMENT_PERIOD_PATH)
    fun getMonthlyStatementPeriod(): Call<MonthlyStatementPeriodEntity>
}
