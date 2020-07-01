package com.aptopayments.mobile.repository.voip.remote

import com.aptopayments.mobile.repository.voip.remote.entities.VoipCallEntity
import com.aptopayments.mobile.repository.voip.remote.requests.GetTokensRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

private const val VOIP_AUTH_PATH = "v1/voip/authorization"

internal interface VoipApi {

    @POST(VOIP_AUTH_PATH)
    fun getTokens(@Body request: GetTokensRequest): Call<VoipCallEntity>
}
