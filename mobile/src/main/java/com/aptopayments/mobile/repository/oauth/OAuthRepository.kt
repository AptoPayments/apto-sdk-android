package com.aptopayments.mobile.repository.oauth

import com.aptopayments.mobile.data.oauth.OAuthAttempt
import com.aptopayments.mobile.data.oauth.OAuthUserDataUpdate
import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.data.workflowaction.AllowedBalanceType
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.platform.BaseRepository
import com.aptopayments.mobile.repository.oauth.remote.OAuthService
import com.aptopayments.mobile.repository.oauth.remote.entities.OAuthAttemptEntity
import com.aptopayments.mobile.repository.oauth.remote.entities.OAuthUserDataUpdateEntity

internal interface OAuthRepository : BaseRepository {

    fun startOAuthAuthentication(allowedBalanceType: AllowedBalanceType): Either<Failure, OAuthAttempt>
    fun getOAuthAttemptStatus(attemptId: String): Either<Failure, OAuthAttempt>
    fun saveOAuthUserData(
        allowedBalanceType: AllowedBalanceType,
        dataPointList: DataPointList,
        tokenId: String
    ): Either<Failure, OAuthUserDataUpdate>

    fun retrieveOAuthUserData(
        allowedBalanceType: AllowedBalanceType,
        tokenId: String
    ): Either<Failure, OAuthUserDataUpdate>

    class Network constructor(
        private val networkHandler: NetworkHandler,
        private val service: OAuthService
    ) : BaseRepository.BaseRepositoryImpl(), OAuthRepository {

        override fun startOAuthAuthentication(allowedBalanceType: AllowedBalanceType): Either<Failure, OAuthAttempt> {
            return when (networkHandler.isConnected) {
                true -> request(
                    service.startOAuthAuthentication(allowedBalanceType = allowedBalanceType),
                    { it.toOAuthAttempt() },
                    OAuthAttemptEntity()
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun getOAuthAttemptStatus(attemptId: String): Either<Failure, OAuthAttempt> {
            return when (networkHandler.isConnected) {
                true -> request(
                    service.getOAuthAttemptStatus(attemptId = attemptId), { it.toOAuthAttempt() }, OAuthAttemptEntity()
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun saveOAuthUserData(
            allowedBalanceType: AllowedBalanceType,
            dataPointList: DataPointList,
            tokenId: String
        ): Either<Failure, OAuthUserDataUpdate> {
            return when (networkHandler.isConnected) {
                true -> request(
                    service.saveOAuthUserData(
                        allowedBalanceType = allowedBalanceType, dataPointList = dataPointList, tokenId = tokenId
                    ),
                    { it.toOAuthUserDataUpdate() },
                    OAuthUserDataUpdateEntity()
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun retrieveOAuthUserData(
            allowedBalanceType: AllowedBalanceType,
            tokenId: String
        ): Either<Failure, OAuthUserDataUpdate> {
            return when (networkHandler.isConnected) {
                true -> request(
                    service.retrieveOAuthUserData(
                        allowedBalanceType = allowedBalanceType,
                        tokenId = tokenId
                    ),
                    { it.toOAuthUserDataUpdate() },
                    OAuthUserDataUpdateEntity()
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}
