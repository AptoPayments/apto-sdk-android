package com.aptopayments.mobile.repository.oauth.remote.entities

import androidx.annotation.VisibleForTesting
import com.aptopayments.mobile.data.oauth.OAuthAttempt
import com.aptopayments.mobile.data.oauth.OAuthAttemptStatus
import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.extension.parseURL
import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.repository.user.remote.entities.DataPointEntity
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Modifier
import java.util.Locale

internal data class OAuthAttemptEntity(

    @SerializedName("id")
    val id: String = "",

    @SerializedName("status")
    val status: String = "",

    @SerializedName("url")
    val url: String = "",

    @SerializedName("oauth_token_id")
    val tokenId: String = "",

    @SerializedName("user_data")
    val userData: ListEntity<DataPointEntity>? = null,

    @SerializedName("error")
    val error: String? = null,

    @SerializedName("error_message")
    val errorMessage: String? = null
) {

    @VisibleForTesting(otherwise = Modifier.PRIVATE)
    fun getUserDataContent(): List<DataPointEntity>? {
        return userData?.data?.filterNotNull()
    }

    fun toOAuthAttempt() = OAuthAttempt(
        id = id,
        status = parseOAuthAttemptStatus(status),
        url = parseURL(url),
        tokenId = tokenId,
        userData = calcUserData(),
        error = error,
        errorMessage = errorMessage
    )

    private fun calcUserData() =
        if (getUserDataContent()?.isEmpty() == false)
            DataPointList(getUserDataContent()?.map { it.toDataPoint() })
        else
            null

    private fun parseOAuthAttemptStatus(status: String): OAuthAttemptStatus {
        return try {
            OAuthAttemptStatus.valueOf(status.uppercase(Locale.US))
        } catch (exception: IllegalArgumentException) {
            OAuthAttemptStatus.PENDING
        }
    }
}
