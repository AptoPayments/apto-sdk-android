package com.aptopayments.mobile.platform

import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.repository.UserSessionRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Call

internal interface BaseRepository : KoinComponent {

    open class BaseRepositoryImpl : BaseRepository {

        protected val userSessionRepository: UserSessionRepository by inject()
        private val requestExecutor: RequestExecutor by inject()

        override fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
            return requestExecutor.execute(call, transform, default)
        }

        override fun <T, R> request(call: Call<T>, transform: (T) -> R): Either<Failure, R> {
            return requestExecutor.execute(call, transform)
        }
    }

    fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R>

    fun <T, R> request(call: Call<T>, transform: (T) -> R): Either<Failure, R>
}
