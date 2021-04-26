package com.aptopayments.mobile.exception

import com.aptopayments.mobile.exception.Failure.FeatureFailure
import com.aptopayments.mobile.extension.localized
import org.json.JSONObject

private const val UNDEFINED_MESSAGE = "error.transport.undefined"

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure(open val errorKey: String = "") {

    fun errorMessage(): String = errorKey.localized()

    object RateLimitFailure : Failure(errorKey = "error_transport_rate_limit")

    object NetworkConnection : Failure(errorKey = "no_network_description")

    object MaintenanceMode : Failure(errorKey = "maintenance_description")

    object DeprecatedSDK : Failure()
    object UserSessionExpired : Failure(errorKey = "session_expired_error")

    open class ServerError(
        val code: Int?,
        private val message: String? = null,
        override val errorKey: String = UNDEFINED_MESSAGE
    ) : Failure() {

        fun hasUndefinedKey() = errorKey == UNDEFINED_MESSAGE

        fun toJSonObject(): JSONObject {
            val rawCode = if (errorKey == UNDEFINED_MESSAGE) "" else (code?.toString() ?: "")

            val json = JSONObject()
            json.put("code", code)
            json.put("message", errorKey.localized())
            json.put("raw_code", rawCode)

            addErrorTracking255CharactersRestriction(json, message)
            return json
        }

        private fun addErrorTracking255CharactersRestriction(json: JSONObject, message: String?) {
            if (!message.isNullOrEmpty()) {
                message
                    .chunked(255)
                    .take(7)
                    .forEachIndexed { index, chunk -> json.put("reason$index", chunk) }
            }
        }
    }

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(override val errorKey: String = "", val titleKey: String = "") : Failure()
}
