package com.aptopayments.mobile.repository.fundingsources.remote

import android.drm.DrmInfoRequest.ACCOUNT_ID
import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.repository.fundingsources.remote.entities.BalanceEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val USER_BALANCES_PATH = "v1/user/accounts/{account_id}/balances"
private const val PAGE = "page"
private const val ROWS = "rows"

internal interface FundingSourcesApi {

    @GET(USER_BALANCES_PATH)
    fun getFundingSources(
        @Path(ACCOUNT_ID) accountID: String,
        @Query(PAGE) page: Int,
        @Query(ROWS) rows: Int
    ): Call<ListEntity<BalanceEntity>>
}
