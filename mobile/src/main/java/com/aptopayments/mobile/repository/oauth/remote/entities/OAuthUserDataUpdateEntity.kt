package com.aptopayments.mobile.repository.oauth.remote.entities

import com.aptopayments.mobile.data.oauth.OAuthUserDataUpdate
import com.aptopayments.mobile.data.oauth.OAuthUserDataUpdateResult
import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.repository.user.remote.entities.DataPointEntity
import com.google.gson.annotations.SerializedName
import java.util.Locale

internal data class OAuthUserDataUpdateEntity(

    @SerializedName("result")
    val result: String = "",

    @SerializedName("user_data")
    val userData: ListEntity<DataPointEntity>? = null

) {
    fun toOAuthUserDataUpdate() = OAuthUserDataUpdate(
        result = parseOAuthUpdateResult(result),
        userData = if (userData?.data?.isEmpty() == false) DataPointList(userData.data?.map { it.toDataPoint() }) else null
    )

    private fun parseOAuthUpdateResult(result: String): OAuthUserDataUpdateResult {
        return try {
            OAuthUserDataUpdateResult.valueOf(result.toUpperCase(Locale.US))
        } catch (exception: Throwable) {
            OAuthUserDataUpdateResult.INVALID
        }
    }
}
