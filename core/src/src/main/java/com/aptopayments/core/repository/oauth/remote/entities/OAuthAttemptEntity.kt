package com.aptopayments.core.repository.oauth.remote.entities

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.oauth.OAuthAttempt
import com.aptopayments.core.data.oauth.OAuthAttemptStatus
import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.extension.parseURL
import com.aptopayments.core.network.ListEntity
import com.aptopayments.core.repository.user.remote.entities.DataPointEntity
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
        userData = if (getUserDataContent()?.isEmpty() == false) DataPointList(getUserDataContent()?.map { it.toDataPoint() }) else null,
        error = error,
        errorMessage = errorMessage
    )

    private fun parseOAuthAttemptStatus(status: String): OAuthAttemptStatus {
        return try {
            OAuthAttemptStatus.valueOf(status.toUpperCase(Locale.US))
        } catch (exception: Throwable) {
            OAuthAttemptStatus.PENDING
        }
    }
}
