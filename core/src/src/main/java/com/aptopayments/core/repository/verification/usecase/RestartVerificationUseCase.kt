package com.aptopayments.core.repository.verification.usecase

import com.aptopayments.core.data.user.Verification
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.verification.VerificationRepository

internal class RestartVerificationUseCase constructor(
    val repository: VerificationRepository,
    networkHandler: NetworkHandler
) : UseCase<Verification, Verification>(networkHandler) {

    override fun run(params: Verification): Either<Failure, Verification> =
        repository.restartVerification(params)
}
