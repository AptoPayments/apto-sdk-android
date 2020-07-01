package com.aptopayments.mobile.repository.verification.remote.entities

import com.aptopayments.mobile.data.PhoneNumber
import com.aptopayments.mobile.data.user.Verification
import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.repository.user.remote.entities.EmailDataPointEntity
import com.aptopayments.mobile.repository.user.remote.entities.PhoneDataPointEntity
import com.aptopayments.mobile.repository.verification.remote.VerificationApi
import com.aptopayments.mobile.repository.verification.remote.entities.request.FinishVerificationRequest
import com.aptopayments.mobile.repository.verification.remote.entities.request.RestartVerificationRequest
import com.aptopayments.mobile.repository.verification.remote.entities.request.StartVerificationRequest
import retrofit2.Call

private const val REQUEST_TYPE_PHONE = "phone"
private const val REQUEST_TYPE_EMAIL = "email"

internal class VerificationService constructor(apiCatalog: ApiCatalog) {

    private val verificationApi by lazy { apiCatalog.api().create(VerificationApi::class.java) }

    fun startVerification(request: PhoneNumber): Call<VerificationEntity> =
        verificationApi.startVerification(prepareDataRequest(request))

    fun startVerification(request: String): Call<VerificationEntity> =
        verificationApi.startVerification(prepareDataRequest(request))

    fun restartVerification(request: Verification): Call<VerificationEntity> =
        verificationApi.restartVerification(
            request.verificationId,
            RestartVerificationRequest(showVerificationSecret = true)
        )

    fun finishVerification(verificationId: String, secret: String): Call<VerificationEntity> =
        verificationApi.finishVerification(verificationId, FinishVerificationRequest(secret))

    private fun prepareDataRequest(phoneNumber: PhoneNumber): StartVerificationRequest =
        StartVerificationRequest(
            datapointType = REQUEST_TYPE_PHONE,
            showVerificationSecret = true,
            datapoint = PhoneDataPointEntity(
                countryCode = phoneNumber.countryCode,
                phoneNumber = phoneNumber.phoneNumber
            )
        )

    private fun prepareDataRequest(emailAddress: String): StartVerificationRequest =
        StartVerificationRequest(
            datapointType = REQUEST_TYPE_EMAIL,
            showVerificationSecret = true,
            datapoint = EmailDataPointEntity(
                email = emailAddress
            )
        )
}
