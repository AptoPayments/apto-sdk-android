package com.aptopayments.core.repository.voip.remote

import com.aptopayments.core.network.X_AUTHORIZATION
import com.aptopayments.core.repository.voip.remote.entities.VoipCallEntity
import com.aptopayments.core.repository.voip.remote.requests.GetTokensRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

private const val VOIP_AUTH_PATH = "v1/voip/authorization"

internal interface VoipApi {

    @POST(VOIP_AUTH_PATH)
    fun getTokens(
            @Header(X_AUTHORIZATION) userToken: String,
            @Body request: GetTokensRequest
    ): Call<VoipCallEntity>
}
