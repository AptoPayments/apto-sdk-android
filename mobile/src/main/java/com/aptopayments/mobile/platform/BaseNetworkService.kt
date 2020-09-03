package com.aptopayments.mobile.platform

import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Call

internal abstract class BaseNetworkService : KoinComponent {

    private val requestExecutor: RequestExecutor by inject()

    protected fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T? = null): Either<Failure, R> {
        return requestExecutor.execute(call, transform, default)
    }
}
