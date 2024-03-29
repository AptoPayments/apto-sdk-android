package com.aptopayments.mobile.repository.stats

import com.aptopayments.mobile.UnitTest
import com.aptopayments.mobile.data.stats.MonthlySpending
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.platform.RequestExecutor
import com.aptopayments.mobile.repository.stats.remote.StatsService
import com.aptopayments.mobile.repository.stats.remote.entities.MonthlySpendingEntity
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import kotlin.test.assertEquals

private const val CARD_ID = "1"
private const val MONTH = "August"
private const val YEAR = "2020"

class StatsRepositoryOldTest : UnitTest() {

    private lateinit var sut: StatsRepositoryImpl
    private lateinit var requestExecutor: RequestExecutor

    private val service: StatsService = mock()
    private val monthlySpending: MonthlySpending = mock()

    @BeforeEach
    fun setUp() {
        startKoin {
            modules(
                module {
                    single { service }
                    single { requestExecutor }
                }
            )
        }
        sut = StatsRepositoryImpl(service)
    }

    @Test
    fun `Get monthly spending should delegate to the service`() {
        given {
            service.getMonthlySpending(
                CARD_ID,
                MONTH,
                YEAR
            )
        }.willReturn(MonthlySpendingEntity().toMonthlySpending().right())

        sut.getMonthlySpending(CARD_ID, MONTH, YEAR)
        verify(service).getMonthlySpending(CARD_ID, MONTH, YEAR)
    }

    @Test
    fun `Get monthly spending should return cached value second time is called`() {
        given { service.getMonthlySpending(CARD_ID, MONTH, YEAR) }.willReturn(monthlySpending.right())

        sut.getMonthlySpending(CARD_ID, MONTH, YEAR)
        val result = sut.getMonthlySpending(CARD_ID, MONTH, YEAR)

        verify(service, times(1)).getMonthlySpending(CARD_ID, MONTH, YEAR)
        assertEquals(result, Either.Right(monthlySpending))
    }

    @Test
    fun `invalidateMonthlySpendingCache makes that the next call it goes through the service`() {
        given { service.getMonthlySpending(CARD_ID, MONTH, YEAR) }.willReturn(monthlySpending.right())

        sut.getMonthlySpending(CARD_ID, MONTH, YEAR)
        sut.invalidateMonthlySpendingCache()
        sut.getMonthlySpending(CARD_ID, MONTH, YEAR)

        verify(service, times(2)).getMonthlySpending(CARD_ID, MONTH, YEAR)
    }
}
