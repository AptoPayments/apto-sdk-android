package com.aptopayments.mobile.repository.stats

import androidx.annotation.VisibleForTesting
import com.aptopayments.mobile.data.stats.MonthlySpending
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.platform.BaseRepository
import com.aptopayments.mobile.repository.stats.remote.StatsService
import com.aptopayments.mobile.repository.stats.remote.entities.MonthlySpendingEntity
import java.lang.reflect.Modifier

internal interface StatsRepository : BaseRepository {

    fun getMonthlySpending(cardId: String, month: String, year: String): Either<Failure, MonthlySpending>
    fun invalidateMonthlySpendingCache(): Either<Failure, Unit>

    class Network constructor(
        private val networkHandler: NetworkHandler,
        private val service: StatsService
    ) : BaseRepository.BaseRepositoryImpl(), StatsRepository {

        @VisibleForTesting(otherwise = Modifier.PRIVATE)
        var monthlySpendingCache: HashMap<Triple<String, String, String>, MonthlySpending> = HashMap()

        override fun getMonthlySpending(cardId: String, month: String, year: String): Either<Failure, MonthlySpending> {
            val dateKey = Triple(cardId, month, year)
            if (monthlySpendingCache.containsKey(dateKey)) {
                return monthlySpendingCache[dateKey]?.let {
                    Either.Right(it)
                } ?: Either.Left(InvalidCachedValueError())
            }
            return when (networkHandler.isConnected) {
                true -> {
                    val result = request(service.getMonthlySpending(cardId, month, year), {
                        it.toMonthlySpending()
                    }, MonthlySpendingEntity())
                    result.either({}, { cacheMonthlySpending(it, dateKey) })
                    return result
                }
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun invalidateMonthlySpendingCache(): Either<Failure, Unit> =
            Either.Right(monthlySpendingCache.clear())

        private fun cacheMonthlySpending(monthlySpending: MonthlySpending, key: Triple<String, String, String>) {
            monthlySpendingCache[key] = monthlySpending
        }
    }
}

internal open class InvalidCachedValueError : Failure.FeatureFailure()
