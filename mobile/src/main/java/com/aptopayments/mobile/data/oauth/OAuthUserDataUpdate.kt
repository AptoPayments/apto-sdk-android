package com.aptopayments.mobile.data.oauth

import com.aptopayments.mobile.data.user.DataPointList
import java.io.Serializable

enum class OAuthUserDataUpdateResult { VALID, INVALID }

data class OAuthUserDataUpdate(
    val result: OAuthUserDataUpdateResult,
    val userData: DataPointList?
) : Serializable
