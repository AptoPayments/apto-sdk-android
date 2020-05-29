package com.aptopayments.core.platform

import android.util.Log
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.network.GsonProvider
import com.aptopayments.core.network.ServerErrorEntity
import com.aptopayments.core.repository.UserSessionRepository
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Call
import retrofit2.Response

internal interface BaseRepository : KoinComponent {

    fun <T> handleError(response: Response<T>): Failure

    open class BaseRepositoryImpl : BaseRepository {

        protected val userSessionRepository: UserSessionRepository by inject()

        override fun <T> handleError(response: Response<T>): Failure {
            return when (response.code()) {
                401 -> {
                    userSessionRepository.clearUserSession()
                    Failure.UserSessionExpired
                }
                412 -> Failure.DeprecatedSDK
                503 -> Failure.MaintenanceMode
                400 -> parseErrorBody(response.errorBody())
                else -> {
                    GsonProvider.provide().toJsonTree(response.body())?.asJsonObject?.let { jsonObject ->
                        return getServerError(jsonObject)
                    }
                    Failure.ServerError(null)
                }
            }
        }
    }

    fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> Either.Left(handleError(response))
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError(null, message = getLog(exception)))
        }
    }

    fun <T, R> request(call: Call<T>, transform: (T) -> R): Either<Failure, R> {
        return try {
            val response = call.execute()

            when {
                response.isSuccessful && response.body() != null -> Either.Right(transform(response.body()!!))
                !response.isSuccessful -> Either.Left(handleError(response))
                else -> Either.Left(Failure.ServerError(null))
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError(null, message = getLog(exception)))
        }
    }

    fun getLog(exception: Throwable) = Log.getStackTraceString(exception).replace("\\n\\t", " ")

    fun parseErrorBody(errorBody: ResponseBody?): Failure.ServerError {
        return errorBody?.let {
            getServerError(JsonParser().parse(errorBody.string()))
        } ?: Failure.ServerError(null)
    }

    fun getServerError(jsonElement: JsonElement): Failure.ServerError {
        return Failure.ServerError(parseServerErrorEntity(jsonElement).code)
    }

    fun parseServerErrorEntity(jsonElement: JsonElement): ServerErrorEntity {
        return GsonProvider.provide().fromJson(jsonElement, ServerErrorEntity::class.java)
    }
}
