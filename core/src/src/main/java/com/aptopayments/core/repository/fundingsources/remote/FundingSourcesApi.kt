package com.aptopayments.core.repository.fundingsources.remote

import android.drm.DrmInfoRequest.ACCOUNT_ID
import com.aptopayments.core.network.ListEntity
import com.aptopayments.core.network.X_API_KEY
import com.aptopayments.core.network.X_AUTHORIZATION
import com.aptopayments.core.repository.fundingsources.remote.entities.BalanceEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

private const val USER_BALANCES_PATH = "v1/user/accounts/{account_id}/balances"

internal interface FundingSourcesApi {

    @GET(USER_BALANCES_PATH)
    fun getFundingSources(
            @Header(X_API_KEY) apiKey: String,
            @Header(X_AUTHORIZATION) userToken: String,
            @Path(ACCOUNT_ID) accountID: String
    ): Call<ListEntity<BalanceEntity>>
}
