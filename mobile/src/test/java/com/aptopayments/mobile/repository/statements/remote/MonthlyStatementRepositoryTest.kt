package com.aptopayments.mobile.repository.statements.remote

import com.aptopayments.mobile.common.ModelDataProvider
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.functional.left
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.repository.statements.MonthlyStatementRepository
import com.aptopayments.mobile.repository.statements.MonthlyStatementRepositoryImpl
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class MonthlyStatementRepositoryTest {
    private val service = mock<MonthlyStatementService>()

    private val sut: MonthlyStatementRepository = MonthlyStatementRepositoryImpl(service)

    @Test
    fun `when monthlyStatePeriod then correct period is retrieved from service`() {
        val period = ModelDataProvider.monthlyStatementsPeriod()
        whenever(service.getMonthlyStatementPeriod()).thenReturn(period.right())

        val result = sut.getMonthlyStatementPeriod()

        verify(service).getMonthlyStatementPeriod()
        result.shouldBeRightAndEqualTo(period)
    }

    @Test
    fun `when monthlyStatePeriod service returns Failure, then failure is returned`() {
        whenever(service.getMonthlyStatementPeriod()).thenReturn(Failure.NetworkConnection.left())

        val result = sut.getMonthlyStatementPeriod()

        assertTrue(result.isLeft)
    }

    @Test
    fun `when monthlyStatePeriod many times then only called once`() {
        val period = ModelDataProvider.monthlyStatementsPeriod()
        whenever(service.getMonthlyStatementPeriod()).thenReturn(period.right())

        sut.getMonthlyStatementPeriod()
        val result = sut.getMonthlyStatementPeriod()

        verify(service).getMonthlyStatementPeriod()
        result.shouldBeRightAndEqualTo(period)
    }

    @Test
    fun `when getMonthlyStatement then correct result returned`() {
        val statement = ModelDataProvider.monthlyStatement()
        val month = 12
        val year = 2019
        whenever(service.getMonthlyStatement(month, year)).thenReturn(statement.right())

        val result = sut.getMonthlyStatement(month, year)

        verify(service).getMonthlyStatement(month, year)
        result.shouldBeRightAndEqualTo(statement)
    }

    @Test
    fun `when getMonthlyStatement service returns Failure, then failure is returned`() {
        whenever(service.getMonthlyStatement(any(), any())).thenReturn(Failure.NetworkConnection.left())

        val result = sut.getMonthlyStatement(12, 2019)

        assertTrue(result.isLeft)
    }
}
