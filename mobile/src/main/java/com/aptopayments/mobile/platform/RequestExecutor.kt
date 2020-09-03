package com.aptopayments.mobile.platform

import android.util.Log
import com.aptopayments.mobile.BuildConfig
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.functional.left
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.network.NetworkHandler
import retrofit2.Call
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

internal class RequestExecutor(
    private val networkHandler: NetworkHandler,
    private val errorHandler: ErrorHandler
) {

    fun <T, R> execute(call: Call<T>, transform: (T) -> R, default: T? = null): Either<Failure, R> {
        return when (networkHandler.isConnected) {
            true -> executeRequest(call, transform, default)
            false -> Failure.NetworkConnection.left()
        }
    }

    private fun <T, R> executeRequest(call: Call<T>, transform: (T) -> R, default: T?): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> processSuccessful(transform, response, default)
                false -> Either.Left(errorHandler.handle(response))
            }
        } catch (exception: Throwable) {
            getFailure(exception)
        }
    }

    private fun <R, T> processSuccessful(
        transform: (T) -> R,
        response: Response<T>,
        default: T?
    ): Either<Failure, R> {
        return when {
            response.body() != null -> transform((response.body()!!)).right()
            default != null -> transform(default).right()
            else -> Failure.ServerError(null).left()
        }
    }

    private fun getFailure(exception: Throwable): Either.Left<Failure> {
        printLogIfIsOnDebugMode(exception)
        return when (exception) {
            is ConnectException, is SocketTimeoutException -> Either.Left(Failure.NetworkConnection)
            else -> Either.Left(Failure.ServerError(null, message = getLog(exception)))
        }
    }

    private fun printLogIfIsOnDebugMode(exception: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.e("APTO", Log.getStackTraceString(exception))
        }
    }

    private fun getLog(exception: Throwable): String? = Log.getStackTraceString(exception)?.replace("\\n\\t", " ")
}
