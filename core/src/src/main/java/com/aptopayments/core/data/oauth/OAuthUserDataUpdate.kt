package com.aptopayments.core.data.oauth

import com.aptopayments.core.data.user.DataPointList
import java.io.Serializable

enum class OAuthUserDataUpdateResult { VALID, INVALID }

data class OAuthUserDataUpdate(
    val result: OAuthUserDataUpdateResult,
    val userData: DataPointList?
) : Serializable
