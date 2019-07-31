package com.aptopayments.core.repository.oauth.remote.entities

import com.aptopayments.core.data.oauth.OAuthCredential
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class OAuthCredentialEntity (

        @SerializedName("credential_type")
        val credentialType: String = "oauth",

        @SerializedName("access")
        val oauthToken: String,

        @SerializedName("refresh")
        val refreshToken: String

) : Serializable {
    fun toOAuthCredential() = OAuthCredential(oauthToken=oauthToken, refreshToken = refreshToken)

    companion object {
        fun from(oauthCredential: OAuthCredential) = OAuthCredentialEntity(
                oauthToken = oauthCredential.oauthToken,
                refreshToken = oauthCredential.refreshToken
        )
    }
}
