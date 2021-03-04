package com.aptopayments.mobile.repository.fundingsources.remote

import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.repository.fundingsources.remote.entities.AchAccountDetailsEntity
import com.aptopayments.mobile.repository.fundingsources.remote.entities.BalanceEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

private const val USER_BALANCES_PATH = "v1/user/accounts/{account_id}/balances"
private const val ACH_ACCOUNT_PATH = "v1/balances/{balance_id}/ach"

private const val ACCOUNT_ID = "account_id"
private const val BALANCE_ID = "balance_id"

private const val PAGE = "page"
private const val ROWS = "rows"

internal interface FundingSourcesApi {

    @GET(USER_BALANCES_PATH)
    fun getFundingSources(
        @Path(ACCOUNT_ID) accountID: String,
        @Query(PAGE) page: Int,
        @Query(ROWS) rows: Int
    ): Call<ListEntity<BalanceEntity>>

    @POST(ACH_ACCOUNT_PATH)
    fun assignAchAccountToBalance(@Path(BALANCE_ID) balanceId: String): Call<AchAccountDetailsEntity>

    @GET(ACH_ACCOUNT_PATH)
    fun getAchAccountDetails(@Path(BALANCE_ID) balanceId: String): Call<AchAccountDetailsEntity>
}
