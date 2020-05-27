package com.aptopayments.core.repository.oauth.remote

import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.data.workflowaction.AllowedBalanceType
import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.platform.BaseService
import com.aptopayments.core.repository.oauth.remote.entities.OAuthAttemptEntity
import com.aptopayments.core.repository.oauth.remote.entities.OAuthUserDataUpdateEntity
import com.aptopayments.core.repository.oauth.remote.requests.RetrieveOAuthUserDataRequest
import com.aptopayments.core.repository.oauth.remote.requests.SaveOAuthUserDataRequest
import com.aptopayments.core.repository.oauth.remote.requests.StartOAuthAuthenticationRequest
import com.aptopayments.core.repository.user.remote.requests.UserDataRequest.Companion.serializeDataPointList
import retrofit2.Call

const val OAUTH_FINISHED_URL = "apto-sdk://oauth-finish"

internal class OAuthService constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val oauthConnectApi by lazy { apiCatalog.api().create(OAuthApi::class.java) }

    fun startOAuthAuthentication(allowedBalanceType: AllowedBalanceType): Call<OAuthAttemptEntity> {
        val request = StartOAuthAuthenticationRequest(
            provider = allowedBalanceType.balanceType,
            baseUri = allowedBalanceType.baseUri.toString(),
            redirectUrl = OAUTH_FINISHED_URL
        )
        return oauthConnectApi.startOAuthAuthentication(request = request)
    }

    fun getOAuthAttemptStatus(attemptId: String): Call<OAuthAttemptEntity> =
        oauthConnectApi.getOAuthAttemptStatus(attemptId = attemptId)

    fun saveOAuthUserData(allowedBalanceType: AllowedBalanceType, dataPointList: DataPointList, tokenId: String):
            Call<OAuthUserDataUpdateEntity> {
        val request = SaveOAuthUserDataRequest(
            provider = allowedBalanceType.balanceType,
            tokenId = tokenId,
            userData = serializeDataPointList(dataPointList)
        )
        return oauthConnectApi.saveOAuthUserData(request = request)
    }

    fun retrieveOAuthUserData(allowedBalanceType: AllowedBalanceType, tokenId: String):
            Call<OAuthUserDataUpdateEntity> {
        val request = RetrieveOAuthUserDataRequest(
            provider = allowedBalanceType.balanceType,
            tokenId = tokenId
        )
        return oauthConnectApi.retrieveOAuthUserData(request = request)
    }
}
