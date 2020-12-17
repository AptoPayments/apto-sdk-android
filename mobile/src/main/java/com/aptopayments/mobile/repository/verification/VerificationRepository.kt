package com.aptopayments.mobile.repository.verification

import com.aptopayments.mobile.data.PhoneNumber
import com.aptopayments.mobile.data.user.Verification
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.functional.Either.Left
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.platform.BaseRepository
import com.aptopayments.mobile.repository.verification.remote.entities.VerificationEntity
import com.aptopayments.mobile.repository.verification.remote.entities.VerificationService

internal interface VerificationRepository : BaseRepository {

    fun startPhoneVerification(params: PhoneNumber): Either<Failure, Verification>
    fun startEmailVerification(params: String): Either<Failure, Verification>
    fun startPrimaryVerification(): Either<Failure, Verification>
    fun restartVerification(params: Verification): Either<Failure, Verification>
    fun finishVerification(verificationId: String, secret: String): Either<Failure, Verification>

    class Network(
        private val networkHandler: NetworkHandler,
        private val service: VerificationService
    ) : BaseRepository.BaseRepositoryImpl(), VerificationRepository {
        override fun startPhoneVerification(params: PhoneNumber): Either<Failure, Verification> {
            return when (networkHandler.isConnected) {
                true ->
                    request(
                        service.startVerification(params),
                        { it.toVerification() }, VerificationEntity()
                    )
                false -> Left(Failure.NetworkConnection)
            }
        }

        override fun startEmailVerification(params: String): Either<Failure, Verification> {
            return when (networkHandler.isConnected) {
                true ->
                    request(
                        service.startVerification(params),
                        { it.toVerification() }, VerificationEntity()
                    )
                false -> Left(Failure.NetworkConnection)
            }
        }

        override fun startPrimaryVerification(): Either<Failure, Verification> {
            return when (networkHandler.isConnected) {
                true ->
                    request(
                        service.startPrimaryVerification(),
                        { it.toVerification() }, VerificationEntity()
                    )
                false -> Left(Failure.NetworkConnection)
            }
        }

        override fun restartVerification(params: Verification): Either<Failure, Verification> {
            return when (networkHandler.isConnected) {
                true ->
                    request(
                        service.restartVerification(params),
                        { it.toVerification() }, VerificationEntity()
                    )
                false -> Left(Failure.NetworkConnection)
            }
        }

        override fun finishVerification(verificationId: String, secret: String): Either<Failure, Verification> {
            return when (networkHandler.isConnected) {
                true ->
                    request(
                        service.finishVerification(verificationId, secret),
                        { it.toVerification() }, VerificationEntity()
                    )
                false -> Left(Failure.NetworkConnection)
            }
        }
    }
}
