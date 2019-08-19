package com.aptopayments.core.data.oauth

import com.aptopayments.core.data.user.DataPointList
import java.io.Serializable
import java.net.URL

enum class OAuthAttemptStatus { PENDING, PASSED, FAILED }

data class OAuthAttempt (
        val id: String,
        val status: OAuthAttemptStatus,
        val url: URL?,
        val tokenId: String,
        var userData: DataPointList?
) : Serializable
