package com.aptopayments.core.repository.transaction.remote

import com.aptopayments.core.network.ListEntity
import com.aptopayments.core.network.X_API_KEY
import com.aptopayments.core.network.X_AUTHORIZATION
import com.aptopayments.core.repository.transaction.remote.entities.TransactionEntity
import retrofit2.Call
import retrofit2.http.*

private const val TRANSACTIONS_PATH = "v1/user/accounts/{account_id}/transactions"
private const val ACCOUNT_ID = "account_id"
private const val STATE = "state"

internal interface TransactionApi {

    @GET(TRANSACTIONS_PATH)
    fun getTransactions(
            @Header(X_API_KEY) apiKey: String,
            @Header(X_AUTHORIZATION) userToken: String,
            @Path(ACCOUNT_ID) cardId: String,
            @QueryMap options: Map<String, String?>,
            @Query(STATE) state: List<String>
    ): Call<ListEntity<TransactionEntity>>
}
