package com.aptopayments.mobile.repository.transaction.remote

import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.repository.transaction.remote.entities.TransactionEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

private const val TRANSACTIONS_PATH = "v1/user/accounts/{account_id}/transactions"
private const val ACCOUNT_ID = "account_id"
private const val STATE = "state"

internal interface TransactionApi {

    @GET(TRANSACTIONS_PATH)
    fun getTransactions(
        @Path(ACCOUNT_ID) cardId: String,
        @QueryMap options: Map<String, String?>,
        @Query(STATE) state: List<String>
    ): Call<ListEntity<TransactionEntity>>
}
