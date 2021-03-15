package com.aptopayments.mobile.repository.verification.remote.entities

import com.aptopayments.mobile.data.PhoneNumber
import com.aptopayments.mobile.data.user.Verification
import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.platform.BaseNetworkService
import com.aptopayments.mobile.repository.user.remote.entities.EmailDataPointEntity
import com.aptopayments.mobile.repository.user.remote.entities.PhoneDataPointEntity
import com.aptopayments.mobile.repository.verification.remote.VerificationApi
import com.aptopayments.mobile.repository.verification.remote.entities.request.FinishVerificationRequest
import com.aptopayments.mobile.repository.verification.remote.entities.request.RestartVerificationRequest
import com.aptopayments.mobile.repository.verification.remote.entities.request.StartVerificationRequest

private const val REQUEST_TYPE_PHONE = "phone"
private const val REQUEST_TYPE_EMAIL = "email"

internal class VerificationService(apiCatalog: ApiCatalog) : BaseNetworkService() {

    private val verificationApi by lazy { apiCatalog.api().create(VerificationApi::class.java) }

    fun startVerification(request: PhoneNumber) =
        request(
            verificationApi.startVerification(prepareDataRequest(request)),
            { it.toVerification() }, VerificationEntity()
        )

    fun startVerification(request: String) =
        request(
            verificationApi.startVerification(prepareDataRequest(request)),
            { it.toVerification() }, VerificationEntity()
        )

    fun startPrimaryVerification() =
        request(
            verificationApi.startPrimaryVerification(),
            { it.toVerification() }, VerificationEntity()
        )

    fun restartVerification(request: Verification) =
        request(
            verificationApi.restartVerification(
                request.verificationId,
                RestartVerificationRequest(showVerificationSecret = true)
            ),
            { it.toVerification() }, VerificationEntity()
        )

    fun finishVerification(verificationId: String, secret: String) =
        request(
            verificationApi.finishVerification(verificationId, FinishVerificationRequest(secret)),
            { it.toVerification() }, VerificationEntity()
        )

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
