package com.aptopayments.core.repository.verification.remote

import com.aptopayments.core.network.X_API_KEY
import com.aptopayments.core.repository.verification.remote.entities.VerificationEntity
import com.aptopayments.core.repository.verification.remote.entities.request.FinishVerificationRequest
import com.aptopayments.core.repository.verification.remote.entities.request.RestartVerificationRequest
import com.aptopayments.core.repository.verification.remote.entities.request.StartVerificationRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

private const val VERIFICATION_START_PATH = "v1/verifications/start"
private const val VERIFICATION_RESTART_PATH = "v1/verifications/{verificationId}/restart"
private const val VERIFICATION_FINISH_PATH = "v1/verifications/{verificationId}/finish"
private const val VERIFICATION_ID = "verificationId"

internal interface VerificationApi {

    @POST(VERIFICATION_START_PATH)
    fun startVerification(
            @Header(X_API_KEY) apiKey: String,
            @Body request: StartVerificationRequest
    ): Call<VerificationEntity>

    @POST(VERIFICATION_RESTART_PATH)
    fun restartVerification(
            @Header(X_API_KEY) apiKey: String,
            @Path(VERIFICATION_ID) verificationID: String,
            @Body request: RestartVerificationRequest
    ): Call<VerificationEntity>

    @POST(VERIFICATION_FINISH_PATH)
    fun finishVerification(
            @Header(X_API_KEY) apiKey: String,
            @Path(VERIFICATION_ID) verificationID: String,
            @Body secret: FinishVerificationRequest
    ): Call<VerificationEntity>
}
