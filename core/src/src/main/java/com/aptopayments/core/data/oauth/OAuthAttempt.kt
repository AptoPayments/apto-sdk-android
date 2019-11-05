package com.aptopayments.core.data.oauth

import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.extension.localized
import java.io.Serializable
import java.net.URL

enum class OAuthAttemptStatus { PENDING, PASSED, FAILED }

data class OAuthAttempt(
        val id: String,
        val status: OAuthAttemptStatus,
        val url: URL?,
        val tokenId: String,
        var userData: DataPointList?,
        val error: String?,
        val errorMessage: String?,
        var errorMessageKeys: List<String>? = null
) : Serializable {
    fun localizedErrorMessage() = errorMessageKey?.localized() ?: ""

    private val errorMessageKey: String?
        get() {
            if (status != OAuthAttemptStatus.FAILED) return null
            val errorKeys = errorMessageKeys ?: defaultErrorMessageKeys

            error?.let {
                val expectedKeyEnd = "$it.message"
                val customKey = errorKeys.firstOrNull { key -> key.endsWith(expectedKeyEnd) }
                if (customKey != null) return customKey
            }
            return errorKeys.firstOrNull { it.endsWith("unknown.message") }
        }

    private val defaultErrorMessageKeys: List<String>
        get() = listOf(
                "select_balance_store.login.error_oauth_invalid_request.message",
                "select_balance_store.login.error_oauth_unauthorised_client.message",
                "select_balance_store.login.error_oauth_access_denied.message",
                "select_balance_store.login.error_oauth_unsupported_response_type.message",
                "select_balance_store.login.error_oauth_invalid_scope.message",
                "select_balance_store.login.error_oauth_server_error.message",
                "select_balance_store.login.error_oauth_temporarily_unavailable.message",
                "select_balance_store.login.error_oauth_unknown.message"
        )
}
