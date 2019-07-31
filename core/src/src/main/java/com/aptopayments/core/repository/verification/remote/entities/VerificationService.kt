package com.aptopayments.core.repository.verification.remote.entities

import com.aptopayments.core.data.PhoneNumber
import com.aptopayments.core.data.user.Verification
import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.repository.user.remote.entities.EmailDataPointEntity
import com.aptopayments.core.repository.user.remote.entities.PhoneDataPointEntity
import com.aptopayments.core.repository.verification.remote.VerificationApi
import com.aptopayments.core.repository.verification.remote.entities.request.FinishVerificationRequest
import com.aptopayments.core.repository.verification.remote.entities.request.RestartVerificationRequest
import com.aptopayments.core.repository.verification.remote.entities.request.StartVerificationRequest
import retrofit2.Call
import javax.inject.Inject

private const val REQUEST_TYPE_PHONE = "phone"
private const val REQUEST_TYPE_EMAIL = "email"

internal class VerificationService
@Inject
constructor(apiCatalog: ApiCatalog) {

    private val verificationApi by lazy { apiCatalog.api().create(VerificationApi::class.java) }

    fun startVerification(request: PhoneNumber): Call<VerificationEntity> =
            verificationApi.startVerification(ApiCatalog.apiKey, prepareDataRequest(request))

    fun startVerification(request: String): Call<VerificationEntity> =
            verificationApi.startVerification(ApiCatalog.apiKey, prepareDataRequest(request))

    fun restartVerification(request: Verification): Call<VerificationEntity> =
            verificationApi.restartVerification(ApiCatalog.apiKey,
                    request.verificationId,
                    RestartVerificationRequest(showVerificationSecret = true))

    fun finishVerification(verificationId: String, secret: String): Call<VerificationEntity> =
            verificationApi.finishVerification(ApiCatalog.apiKey,
                    verificationId,
                    FinishVerificationRequest(secret))

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
