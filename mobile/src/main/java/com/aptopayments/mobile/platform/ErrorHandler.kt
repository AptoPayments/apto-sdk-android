package com.aptopayments.mobile.platform

import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.network.GsonProvider
import com.aptopayments.mobile.network.ServerErrorEntity
import com.aptopayments.mobile.repository.UserSessionRepository
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

internal class ErrorHandler(private val userSessionRepository: UserSessionRepository) {

    fun <T> handle(response: Response<T>): Failure {
        return try {
            when (response.code()) {
                400 -> parseErrorBody(response.errorBody())
                401 -> userSessionExpired()
                412 -> Failure.DeprecatedSDK
                429 -> Failure.RateLimitFailure
                503 -> Failure.MaintenanceMode
                else -> parseErrorBody(response.errorBody())
            }
        } catch (e: Exception) {
            genericServerError()
        }
    }

    private fun userSessionExpired(): Failure.UserSessionExpired {
        userSessionRepository.clearUserSession()
        return Failure.UserSessionExpired
    }

    private fun parseErrorBody(errorBody: ResponseBody?): Failure.ServerError {
        return errorBody?.let {
            getServerError(JsonParser().parse(errorBody.string()))
        } ?: genericServerError()
    }

    private fun genericServerError() = Failure.ServerError(null)

    private fun getServerError(jsonElement: JsonElement): Failure.ServerError {
        return Failure.ServerError(parseServerErrorEntity(jsonElement).code)
    }

    private fun parseServerErrorEntity(jsonElement: JsonElement): ServerErrorEntity {
        return GsonProvider.provide().fromJson(jsonElement, ServerErrorEntity::class.java)
    }
}
