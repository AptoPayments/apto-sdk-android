package com.aptopayments.mobile.repository.oauth

import com.aptopayments.mobile.data.oauth.OAuthAttempt
import com.aptopayments.mobile.data.oauth.OAuthUserDataUpdate
import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.data.workflowaction.AllowedBalanceType
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.platform.BaseNoNetworkRepository
import com.aptopayments.mobile.repository.oauth.remote.OAuthService

internal interface OAuthRepository : BaseNoNetworkRepository {

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
}

internal class OAuthRepositoryImpl(
    private val service: OAuthService
) : OAuthRepository {

    override fun startOAuthAuthentication(allowedBalanceType: AllowedBalanceType) =
        service.startOAuthAuthentication(allowedBalanceType = allowedBalanceType)

    override fun getOAuthAttemptStatus(attemptId: String) =
        service.getOAuthAttemptStatus(attemptId = attemptId)

    override fun saveOAuthUserData(
        allowedBalanceType: AllowedBalanceType,
        dataPointList: DataPointList,
        tokenId: String
    ) = service.saveOAuthUserData(
        allowedBalanceType = allowedBalanceType, dataPointList = dataPointList, tokenId = tokenId
    )

    override fun retrieveOAuthUserData(
        allowedBalanceType: AllowedBalanceType,
        tokenId: String
    ) = service.retrieveOAuthUserData(
        allowedBalanceType = allowedBalanceType,
        tokenId = tokenId
    )
}
