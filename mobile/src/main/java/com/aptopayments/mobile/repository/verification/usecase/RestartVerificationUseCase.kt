package com.aptopayments.mobile.repository.verification.usecase

import com.aptopayments.mobile.data.user.Verification
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.verification.VerificationRepository

internal class RestartVerificationUseCase constructor(
    val repository: VerificationRepository,
    networkHandler: NetworkHandler
) : UseCase<Verification, Verification>(networkHandler) {

    override fun run(params: Verification): Either<Failure, Verification> =
        repository.restartVerification(params)
}
