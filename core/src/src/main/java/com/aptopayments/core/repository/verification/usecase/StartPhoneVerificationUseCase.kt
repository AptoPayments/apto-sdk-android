package com.aptopayments.core.repository.verification.usecase

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.PhoneNumber
import com.aptopayments.core.data.user.Verification
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.verification.VerificationRepository
import java.lang.reflect.Modifier
import javax.inject.Inject

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class StartPhoneVerificationUseCase @Inject constructor(
        private val repository: VerificationRepository,
        networkHandler: NetworkHandler
) : UseCase<Verification, PhoneNumber>(networkHandler)
{

    override fun run(params: PhoneNumber): Either<Failure, Verification>
            = repository.startPhoneVerification(params)

}
