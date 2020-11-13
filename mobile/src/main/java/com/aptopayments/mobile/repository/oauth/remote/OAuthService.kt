package com.aptopayments.mobile.repository.oauth.remote

import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.data.workflowaction.AllowedBalanceType
import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.platform.BaseNetworkService
import com.aptopayments.mobile.repository.oauth.remote.entities.OAuthAttemptEntity
import com.aptopayments.mobile.repository.oauth.remote.entities.OAuthUserDataUpdateEntity
import com.aptopayments.mobile.repository.oauth.remote.requests.RetrieveOAuthUserDataRequest
import com.aptopayments.mobile.repository.oauth.remote.requests.SaveOAuthUserDataRequest
import com.aptopayments.mobile.repository.oauth.remote.requests.StartOAuthAuthenticationRequest
import com.aptopayments.mobile.repository.user.remote.requests.UserDataRequest.Companion.serializeDataPointList

const val OAUTH_FINISHED_URL = "apto-sdk://oauth-finish"

internal class OAuthService constructor(apiCatalog: ApiCatalog) : BaseNetworkService() {

    private val oauthConnectApi by lazy { apiCatalog.api().create(OAuthApi::class.java) }

    fun startOAuthAuthentication(allowedBalanceType: AllowedBalanceType) =
        request(
            oauthConnectApi.startOAuthAuthentication(
                StartOAuthAuthenticationRequest(
                    provider = allowedBalanceType.balanceType,
                    baseUri = allowedBalanceType.baseUri.toString(),
                    redirectUrl = OAUTH_FINISHED_URL
                )
            ),
            { it.toOAuthAttempt() },
            OAuthAttemptEntity()
        )

    fun getOAuthAttemptStatus(attemptId: String) =
        request(
            oauthConnectApi.getOAuthAttemptStatus(attemptId = attemptId),
            { it.toOAuthAttempt() },
            OAuthAttemptEntity()
        )

    fun saveOAuthUserData(allowedBalanceType: AllowedBalanceType, dataPointList: DataPointList, tokenId: String) =
        request(
            oauthConnectApi.saveOAuthUserData(
                SaveOAuthUserDataRequest(
                    provider = allowedBalanceType.balanceType,
                    tokenId = tokenId,
                    userData = serializeDataPointList(dataPointList)
                )
            ),
            { it.toOAuthUserDataUpdate() },
            OAuthUserDataUpdateEntity()
        )

    fun retrieveOAuthUserData(allowedBalanceType: AllowedBalanceType, tokenId: String) =
        request(
            oauthConnectApi.retrieveOAuthUserData(
                RetrieveOAuthUserDataRequest(
                    provider = allowedBalanceType.balanceType,
                    tokenId = tokenId
                )
            ),
            { it.toOAuthUserDataUpdate() },
            OAuthUserDataUpdateEntity()
        )
}
