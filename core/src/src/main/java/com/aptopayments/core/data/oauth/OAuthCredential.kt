package com.aptopayments.core.data.oauth

import java.io.Serializable

class OAuthCredential(
    val oauthToken: String,
    val refreshToken: String
) : Serializable
