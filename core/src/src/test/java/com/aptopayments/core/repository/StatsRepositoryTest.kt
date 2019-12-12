package com.aptopayments.core.repository

import com.aptopayments.core.UnitTest
import com.aptopayments.core.data.stats.MonthlySpending
import com.aptopayments.core.exception.Failure.NetworkConnection
import com.aptopayments.core.functional.Either
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.stats.StatsRepository
import com.aptopayments.core.repository.stats.remote.StatsService
import com.aptopayments.core.repository.stats.remote.entities.MonthlySpendingEntity
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response
import kotlin.test.assertEquals

class StatsRepositoryTest : UnitTest() {

    private lateinit var sut: StatsRepository.Network

    @Mock private lateinit var networkHandler: NetworkHandler
    @Mock private lateinit var service: StatsService

    @Mock private lateinit var getMonthlySpendingCall: Call<MonthlySpendingEntity>
    @Mock private lateinit var getMonthlySpendingResponse: Response<MonthlySpendingEntity>
    @Mock private lateinit var monthlySpending: MonthlySpending

    @Before
    override fun setUp() {
        super.setUp()
        startKoin {
            modules(module {
                single { networkHandler }
                single { service }
            })
        }
        sut = StatsRepository.Network(networkHandler, service)
    }

    @Test fun `Get monthly spending should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val result = sut.getMonthlySpending("", "", "")

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test fun `Get monthly spending should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(null)

        val result = sut.getMonthlySpending("", "", "")

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test fun `Get monthly spending should delegate to the service`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { getMonthlySpendingResponse.body() }.willReturn(MonthlySpendingEntity())
        given { getMonthlySpendingResponse.isSuccessful }.willReturn(true)
        given { getMonthlySpendingCall.execute() }.willReturn(getMonthlySpendingResponse)
        given { service.getMonthlySpending("", "", "") }.willReturn(getMonthlySpendingCall)

        sut.getMonthlySpending("", "", "")
        verify(service).getMonthlySpending("", "", "")
    }

    @Test fun `Get monthly spending should return cached value if present`() {
        // Given
        val cardId = "cardId"
        val month = "month"
        val year = "year"
        sut.monthlySpendingCache[Triple(cardId, month, year)] = monthlySpending

        // When
        val result = sut.getMonthlySpending(cardId, month, year)

        // Then
        verifyZeroInteractions(service)
        assertEquals(result, Either.Right(monthlySpending))
    }

    @Test fun `Invalidate cache should clear all stored values`() {
        // Given
        val cardId = "cardId"
        val month = "month"
        val year = "year"
        sut.monthlySpendingCache[Triple(cardId, month, year)] = monthlySpending

        // When
        sut.invalidateMonthlySpendingCache()

        // Then
        assert(sut.monthlySpendingCache.isEmpty())
    }
}
