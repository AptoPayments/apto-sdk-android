package com.aptopayments.mobile.repository.statements

import com.aptopayments.mobile.data.statements.MonthlyStatement
import com.aptopayments.mobile.data.statements.MonthlyStatementPeriod
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.platform.BaseNoNetworkRepository
import com.aptopayments.mobile.repository.statements.remote.MonthlyStatementService
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

internal interface MonthlyStatementRepository : BaseNoNetworkRepository {
    fun getMonthlyStatement(month: Int, year: Int): Either<Failure, MonthlyStatement>
    fun getMonthlyStatementPeriod(): Either<Failure, MonthlyStatementPeriod>
}

internal class MonthlyStatementRepositoryImpl(private val service: MonthlyStatementService) :
    MonthlyStatementRepository {
    private var periodCache: MonthlyStatementPeriod? = null
    private var periodCacheDayNumber: Int = -1

    override fun getMonthlyStatement(month: Int, year: Int): Either<Failure, MonthlyStatement> {
        return service.getMonthlyStatement(month, year)
    }

    @Synchronized
    override fun getMonthlyStatementPeriod(): Either<Failure, MonthlyStatementPeriod> {
        return if (isPeriodCacheValid()) {
            Either.Right(periodCache!!)
        } else {
            return service.getMonthlyStatementPeriod().runIfRight { cachePeriod(it) }
        }
    }

    private fun cachePeriod(period: MonthlyStatementPeriod) {
        periodCache = period
        periodCacheDayNumber = getCurrentDayOfTheYear()
    }

    private fun isPeriodCacheValid() = periodCacheDayNumber == getCurrentDayOfTheYear()

    private fun getCurrentDayOfTheYear() = ZonedDateTime.now(ZoneOffset.UTC).dayOfYear
}
