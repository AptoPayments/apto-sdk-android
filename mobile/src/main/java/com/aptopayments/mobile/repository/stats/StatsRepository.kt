package com.aptopayments.mobile.repository.stats

import com.aptopayments.mobile.data.stats.MonthlySpending
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.platform.BaseNoNetworkRepository
import com.aptopayments.mobile.repository.stats.remote.StatsService

internal interface StatsRepository {
    fun getMonthlySpending(cardId: String, month: String, year: String): Either<Failure, MonthlySpending>
    fun invalidateMonthlySpendingCache(): Either<Failure, Unit>
}

internal class StatsRepositoryImpl(private val service: StatsService) : StatsRepository, BaseNoNetworkRepository {

    private val monthlySpendingCache: HashMap<Triple<String, String, String>, MonthlySpending> = HashMap()

    override fun getMonthlySpending(cardId: String, month: String, year: String): Either<Failure, MonthlySpending> {
        val dateKey = Triple(cardId, month, year)
        return if (monthlySpendingCache.containsKey(dateKey)) {
            return monthlySpendingCache[dateKey]!!.right()
        } else {
            service.getMonthlySpending(cardId, month, year).runIfRight { cacheMonthlySpending(it, dateKey) }
        }
    }

    override fun invalidateMonthlySpendingCache(): Either<Failure, Unit> {
        monthlySpendingCache.clear()
        return Unit.right()
    }

    private fun cacheMonthlySpending(monthlySpending: MonthlySpending, key: Triple<String, String, String>) {
        monthlySpendingCache[key] = monthlySpending
    }
}
