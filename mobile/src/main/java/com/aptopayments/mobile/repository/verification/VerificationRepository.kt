package com.aptopayments.mobile.repository.verification

import com.aptopayments.mobile.data.PhoneNumber
import com.aptopayments.mobile.data.user.Verification
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.platform.BaseNoNetworkRepository
import com.aptopayments.mobile.repository.verification.remote.entities.VerificationService

internal interface VerificationRepository : BaseNoNetworkRepository {
    fun startPhoneVerification(params: PhoneNumber): Either<Failure, Verification>
    fun startEmailVerification(params: String): Either<Failure, Verification>
    fun startPrimaryVerification(): Either<Failure, Verification>
    fun restartVerification(params: Verification): Either<Failure, Verification>
    fun finishVerification(verificationId: String, secret: String): Either<Failure, Verification>
}

internal class VerificationRepositoryImpl(private val service: VerificationService) : VerificationRepository {
    override fun startPhoneVerification(params: PhoneNumber) = service.startVerification(params)
    override fun startEmailVerification(params: String) = service.startVerification(params)
    override fun startPrimaryVerification() = service.startPrimaryVerification()
    override fun restartVerification(params: Verification) = service.restartVerification(params)
    override fun finishVerification(verificationId: String, secret: String) =
        service.finishVerification(verificationId, secret)
}
