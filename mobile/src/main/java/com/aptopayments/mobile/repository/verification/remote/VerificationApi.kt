package com.aptopayments.mobile.repository.verification.remote

import com.aptopayments.mobile.repository.verification.remote.entities.VerificationEntity
import com.aptopayments.mobile.repository.verification.remote.entities.request.FinishVerificationRequest
import com.aptopayments.mobile.repository.verification.remote.entities.request.RestartVerificationRequest
import com.aptopayments.mobile.repository.verification.remote.entities.request.StartVerificationRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

private const val VERIFICATION_START_PATH = "v1/verifications/start"
private const val VERIFICATION_RESTART_PATH = "v1/verifications/{verificationId}/restart"
private const val VERIFICATION_FINISH_PATH = "v1/verifications/{verificationId}/finish"
private const val VERIFICATION_ID = "verificationId"

internal interface VerificationApi {

    @POST(VERIFICATION_START_PATH)
    fun startVerification(
        @Body request: StartVerificationRequest
    ): Call<VerificationEntity>

    @POST(VERIFICATION_RESTART_PATH)
    fun restartVerification(
        @Path(VERIFICATION_ID) verificationID: String,
        @Body request: RestartVerificationRequest
    ): Call<VerificationEntity>

    @POST(VERIFICATION_FINISH_PATH)
    fun finishVerification(
        @Path(VERIFICATION_ID) verificationID: String,
        @Body secret: FinishVerificationRequest
    ): Call<VerificationEntity>
}
