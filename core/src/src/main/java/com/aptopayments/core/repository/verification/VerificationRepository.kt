package com.aptopayments.core.repository.verification

import com.aptopayments.core.data.PhoneNumber
import com.aptopayments.core.data.user.Verification
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.functional.Either.Left
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.platform.BaseRepository
import com.aptopayments.core.repository.verification.remote.entities.VerificationEntity
import com.aptopayments.core.repository.verification.remote.entities.VerificationService
import javax.inject.Inject

internal interface VerificationRepository: BaseRepository {

    fun startPhoneVerification(params: PhoneNumber): Either<Failure, Verification>
    fun startEmailVerification(params: String): Either<Failure, Verification>
    fun restartVerification(params: Verification): Either<Failure, Verification>
    fun finishVerification(verificationId: String, secret: String): Either<Failure, Verification>

    class Network
    @Inject constructor(private val networkHandler: NetworkHandler,
                        private val service: VerificationService
    ) : BaseRepository.BaseRepositoryImpl(),
        VerificationRepository {
        override fun startPhoneVerification(params: PhoneNumber): Either<Failure, Verification> {
            return when (networkHandler.isConnected) {
                true -> request(service.startVerification(params),
                        { it.toVerification() }, VerificationEntity())
                false, null -> Left(Failure.NetworkConnection)
            }
        }

        override fun startEmailVerification(params: String): Either<Failure, Verification> {
            return when (networkHandler.isConnected) {
                true -> request(service.startVerification(params),
                        { it.toVerification() }, VerificationEntity())
                false, null -> Left(Failure.NetworkConnection)
            }
        }

        override fun restartVerification(params: Verification): Either<Failure, Verification> {
            return when (networkHandler.isConnected) {
                true -> request(service.restartVerification(params),
                        { it.toVerification() }, VerificationEntity())
                false, null -> Left(Failure.NetworkConnection)
            }
        }

        override fun finishVerification(verificationId: String, secret: String): Either<Failure, Verification> {
            return when (networkHandler.isConnected) {
                true -> request(service.finishVerification(verificationId, secret),
                        { it.toVerification() }, VerificationEntity())
                false, null -> Left(Failure.NetworkConnection)
            }
        }
    }
}
