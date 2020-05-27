package com.aptopayments.core.repository.statements

import com.aptopayments.core.UnitTest
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.statements.remote.MonthlyStatementService
import com.aptopayments.core.repository.statements.remote.entities.MonthlyStatementPeriodEntity
import com.aptopayments.core.repository.statements.remote.entities.MonthlyStatementReportEntity
import com.aptopayments.core.repository.statements.remote.entities.StatementMonthEntity
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
import kotlin.test.assertTrue

const val MONTH = 10
const val YEAR = 2019

class MonthlyStatementRepositoryTest : UnitTest() {

    private lateinit var network: MonthlyStatementRepository.Network

    @Mock
    private lateinit var networkHandler: NetworkHandler

    @Mock
    private lateinit var service: MonthlyStatementService

    @Mock
    private lateinit var getStatementCall: Call<MonthlyStatementReportEntity>

    @Mock
    private lateinit var getStatementResponse: Response<MonthlyStatementReportEntity>

    @Mock
    private lateinit var getStatementPeriodCall: Call<MonthlyStatementPeriodEntity>

    @Mock
    private lateinit var getStatementPeriodResponse: Response<MonthlyStatementPeriodEntity>

    @Before
    override fun setUp() {
        super.setUp()
        startKoin {
            modules(module {
                single { networkHandler }
                single { service }
            })
        }
        network = MonthlyStatementRepository.Network(networkHandler, service)
    }

    @Test
    fun `getMonthlyStatement should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val result = network.getMonthlyStatement(MONTH, YEAR)

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either(
            { failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java },
            {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `getMonthlyStatement should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val result = network.getMonthlyStatement(MONTH, YEAR)

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either(
            { failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java },
            {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `getMonthlyStatement should delegate to the service`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { getStatementResponse.body() }.willReturn(MonthlyStatementReportEntity())
        given { getStatementResponse.isSuccessful }.willReturn(true)
        given { getStatementCall.execute() }.willReturn(getStatementResponse)
        given { service.getMonthlyStatement(MONTH, YEAR) }.willReturn(getStatementCall)

        val result = network.getMonthlyStatement(MONTH, YEAR)
        verify(service).getMonthlyStatement(MONTH, YEAR)
        assertTrue { result.isRight }
    }

    @Test
    fun `getMonthlyStatementPeriod should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val result = network.getMonthlyStatementPeriod()

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either(
            { failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java },
            {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `getMonthlyStatementPeriod should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val result = network.getMonthlyStatementPeriod()

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `getMonthlyStatementPeriod should delegate to the service`() {
        val start = StatementMonthEntity(2, 2019)
        val end = StatementMonthEntity(10, 2019)

        val entity = MonthlyStatementPeriodEntity(start, end)

        given { networkHandler.isConnected }.willReturn(true)
        given { getStatementPeriodResponse.body() }.willReturn(entity)
        given { getStatementPeriodResponse.isSuccessful }.willReturn(true)
        given { getStatementPeriodCall.execute() }.willReturn(getStatementPeriodResponse)
        given { service.getMonthlyStatementPeriod() }.willReturn(getStatementPeriodCall)

        val result = network.getMonthlyStatementPeriod()
        verify(service).getMonthlyStatementPeriod()
        assertTrue { result.isRight }
    }
}
