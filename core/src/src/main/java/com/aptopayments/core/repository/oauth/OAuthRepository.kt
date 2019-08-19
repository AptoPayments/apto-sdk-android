package com.aptopayments.core.repository.oauth

import com.aptopayments.core.data.oauth.OAuthAttempt
import com.aptopayments.core.data.oauth.OAuthUserDataUpdate
import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.data.workflowaction.AllowedBalanceType
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.platform.BaseRepository
import com.aptopayments.core.repository.oauth.remote.OAuthService
import com.aptopayments.core.repository.oauth.remote.entities.OAuthAttemptEntity
import com.aptopayments.core.repository.oauth.remote.entities.OAuthUserDataUpdateEntity

internal interface OAuthRepository : BaseRepository {

    fun startOAuthAuthentication(allowedBalanceType: AllowedBalanceType): Either<Failure, OAuthAttempt>
    fun getOAuthAttemptStatus(attemptId: String): Either<Failure, OAuthAttempt>
    fun saveOAuthUserData(allowedBalanceType: AllowedBalanceType, dataPointList: DataPointList, tokenId: String): Either<Failure, OAuthUserDataUpdate>
    fun retrieveOAuthUserData(allowedBalanceType: AllowedBalanceType, tokenId: String): Either<Failure, OAuthUserDataUpdate>

    class Network constructor(
            private val networkHandler: NetworkHandler,
            private val service: OAuthService
    ) : BaseRepository.BaseRepositoryImpl(), OAuthRepository {

        override fun startOAuthAuthentication(allowedBalanceType: AllowedBalanceType): Either<Failure, OAuthAttempt> {
            return when (networkHandler.isConnected) {
                true -> request(service.startOAuthAuthentication(
                        allowedBalanceType = allowedBalanceType), { it.toOAuthAttempt() }, OAuthAttemptEntity())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun getOAuthAttemptStatus(attemptId: String): Either<Failure, OAuthAttempt> {
            return when (networkHandler.isConnected) {
                true -> request(service.getOAuthAttemptStatus(
                        attemptId = attemptId), { it.toOAuthAttempt() }, OAuthAttemptEntity())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun saveOAuthUserData(allowedBalanceType: AllowedBalanceType, dataPointList: DataPointList, tokenId: String): Either<Failure, OAuthUserDataUpdate> {
            return when (networkHandler.isConnected) {
                true -> request(service.saveOAuthUserData(
                        allowedBalanceType = allowedBalanceType, dataPointList = dataPointList, tokenId = tokenId),
                        { it.toOAuthUserDataUpdate() }, OAuthUserDataUpdateEntity())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun retrieveOAuthUserData(allowedBalanceType: AllowedBalanceType, tokenId: String): Either<Failure, OAuthUserDataUpdate> {
            return when (networkHandler.isConnected) {
                true -> request(service.retrieveOAuthUserData(
                        allowedBalanceType = allowedBalanceType,
                        tokenId = tokenId),
                        { it.toOAuthUserDataUpdate() }, OAuthUserDataUpdateEntity())
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}
