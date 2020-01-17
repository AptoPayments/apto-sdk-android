package com.aptopayments.core.repository.statements

import com.aptopayments.core.data.statements.MonthlyStatement
import com.aptopayments.core.data.statements.MonthlyStatementPeriod
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.platform.BaseRepository
import com.aptopayments.core.repository.statements.remote.MonthlyStatementService
import com.aptopayments.core.repository.statements.remote.entities.MonthlyStatementReportEntity
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

internal interface MonthlyStatementRepository : BaseRepository {

    fun getMonthlyStatement(month: Int, year: Int): Either<Failure, MonthlyStatement>
    fun getMonthlyStatementPeriod(): Either<Failure, MonthlyStatementPeriod>

    class Network constructor(
        private val networkHandler: NetworkHandler,
        private val service: MonthlyStatementService
    ) : BaseRepository.BaseRepositoryImpl(), MonthlyStatementRepository {

        private var periodCache: MonthlyStatementPeriod? = null
        private var periodCacheDayNumber: Int = -1

        override fun getMonthlyStatement(month: Int, year: Int): Either<Failure, MonthlyStatement> {
            return when (networkHandler.isConnected) {
                true -> {
                    request(service.getMonthlyStatement(month, year), {
                        it.toMonthlyStatementReport()
                    }, MonthlyStatementReportEntity(month, year))
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        @Synchronized
        override fun getMonthlyStatementPeriod(): Either<Failure, MonthlyStatementPeriod> {
            return if (isPeriodCacheValid()) {
                Either.Right(periodCache!!)
            } else {
                when (networkHandler.isConnected) {
                    true -> request(service.getMonthlyStatementPeriod()) {
                        cachePeriod(it.toMonthlyStatementPeriod())
                        periodCache!!
                    }
                    else -> Either.Left(Failure.NetworkConnection)
                }
            }
        }

        private fun cachePeriod(period: MonthlyStatementPeriod) {
            periodCache = period
            periodCacheDayNumber = getCurrentDayOfTheYear()
        }

        private fun isPeriodCacheValid() = periodCacheDayNumber == getCurrentDayOfTheYear()

        private fun getCurrentDayOfTheYear() = ZonedDateTime.now(ZoneOffset.UTC).dayOfYear
    }
}
