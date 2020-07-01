package com.aptopayments.mobile.repository.stats.remote

import com.aptopayments.mobile.repository.stats.remote.entities.MonthlySpendingEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val MONTHLY_SPENDING_STATS_PATH = "v1/user/accounts/{account_id}/stats/monthly_spending"
private const val ACCOUNT_ID = "account_id"
private const val MONTH = "month"
private const val YEAR = "year"

internal interface StatsApi {

    @GET(MONTHLY_SPENDING_STATS_PATH)
    fun getMonthlySpending(
        @Path(ACCOUNT_ID) cardId: String,
        @Query(MONTH) month: String,
        @Query(YEAR) year: String
    ): Call<MonthlySpendingEntity>
}
