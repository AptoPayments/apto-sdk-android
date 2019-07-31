package com.aptopayments.core.repository.oauth.remote

import com.aptopayments.core.network.X_API_KEY
import com.aptopayments.core.network.X_AUTHORIZATION
import com.aptopayments.core.repository.oauth.remote.entities.OAuthAttemptEntity
import com.aptopayments.core.repository.oauth.remote.entities.OAuthUserDataUpdateEntity
import com.aptopayments.core.repository.oauth.remote.requests.RetrieveOAuthUserDataRequest
import com.aptopayments.core.repository.oauth.remote.requests.SaveOAuthUserDataRequest
import com.aptopayments.core.repository.oauth.remote.requests.StartOAuthAuthenticationRequest
import retrofit2.Call
import retrofit2.http.*

private const val START_OAUTH_PATH = "v1/oauth"
private const val ATTEMPT_ID = "attemptId"
private const val GET_OAUTH_ATTEMPT = "v1/oauth/{attemptId}"
private const val SAVE_OAUTH_USER_DATA = "/v1/oauth/userdata/save"
private const val RETRIEVE_OAUTH_USER_DATA = "/v1/oauth/userdata/retrieve"

internal interface OAuthApi {

    @POST(START_OAUTH_PATH)
    fun startOAuthAuthentication(
            @Header(X_API_KEY) apiKey: String,
            @Header(X_AUTHORIZATION) userToken: String,
            @Body request: StartOAuthAuthenticationRequest
    ): Call<OAuthAttemptEntity>

    @GET(GET_OAUTH_ATTEMPT)
    fun getOAuthAttemptStatus(
            @Header(X_API_KEY) apiKey: String,
            @Header(X_AUTHORIZATION) userToken: String,
            @Path(ATTEMPT_ID) attemptId: String
    ): Call<OAuthAttemptEntity>

    @POST(SAVE_OAUTH_USER_DATA)
    fun saveOAuthUserData(
            @Header(X_API_KEY) apiKey: String,
            @Header(X_AUTHORIZATION) userToken: String,
            @Body request: SaveOAuthUserDataRequest
    ): Call<OAuthUserDataUpdateEntity>

    @POST(RETRIEVE_OAUTH_USER_DATA)
    fun retrieveOAuthUserData(
            @Header(X_API_KEY) apiKey: String,
            @Header(X_AUTHORIZATION) userToken: String,
            @Body request: RetrieveOAuthUserDataRequest
    ): Call<OAuthUserDataUpdateEntity>
}
