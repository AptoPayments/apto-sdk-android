package com.aptopayments.mobile.platform

import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.exception.server.ServerErrorFactory
import com.aptopayments.mobile.network.GsonProvider
import com.aptopayments.mobile.network.ServerErrorEntity
import com.aptopayments.mobile.repository.UserSessionRepository
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

private const val BAD_REQUEST_400 = 400
private const val UNAUTHORIZED_401 = 401
private const val PRECONDITION_FAILED_412 = 412
private const val TOO_MANY_REQUESTS_429 = 429
private const val SERVICE_UNAVAILABLE_503 = 503

internal class ErrorHandler(
    private val userSessionRepository: UserSessionRepository,
    private val serverErrorFactory: ServerErrorFactory
) {
    fun <T> handle(response: Response<T>): Failure {
        return try {
            when (response.code()) {
                BAD_REQUEST_400 -> parseErrorBody(response.errorBody())
                UNAUTHORIZED_401 -> userSessionExpired()
                PRECONDITION_FAILED_412 -> Failure.DeprecatedSDK
                TOO_MANY_REQUESTS_429 -> Failure.RateLimitFailure
                SERVICE_UNAVAILABLE_503 -> Failure.MaintenanceMode
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
        return serverErrorFactory.create(parseServerErrorEntity(jsonElement).code)
    }

    private fun parseServerErrorEntity(jsonElement: JsonElement): ServerErrorEntity {
        return GsonProvider.provide().fromJson(jsonElement, ServerErrorEntity::class.java)
    }
}
