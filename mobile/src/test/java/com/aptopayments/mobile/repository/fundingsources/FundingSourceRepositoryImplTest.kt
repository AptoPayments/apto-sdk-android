package com.aptopayments.mobile.repository.fundingsources

import com.aptopayments.mobile.data.fundingsources.AchAccountDetails
import com.aptopayments.mobile.data.fundingsources.Balance
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.repository.fundingsources.local.BalanceLocalDao
import com.aptopayments.mobile.repository.fundingsources.local.entities.BalanceLocalEntity
import com.aptopayments.mobile.repository.fundingsources.remote.FundingSourcesService
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

private const val ACCOUNT_ID = "ac_1234"
private const val BALANCE_ID = "bl_12345"

internal class FundingSourceRepositoryImplTest {

    private val service: FundingSourcesService = mock()
    private val balanceLocalDao: BalanceLocalDao = mock()

    private val achAccountDetails: AchAccountDetails = mock()

    private val sut = FundingSourceRepositoryImpl(service, balanceLocalDao)

    @Test
    fun `when getFundingSources with refresh then service gets called`() {
        mockGetFundingService()

        sut.getFundingSources(ACCOUNT_ID, true, 0, 10)

        verify(service).getFundingSources(ACCOUNT_ID, 0, 10)
    }

    @Test
    fun `when getFundingSources and false refresh but null data saved then service gets called`() {
        mockGetFundingService()
        whenever(balanceLocalDao.getBalances()).thenReturn(null)

        sut.getFundingSources(accountId = ACCOUNT_ID, refresh = true, page = 0, rows = 10)

        verify(service).getFundingSources(ACCOUNT_ID, 0, 10)
    }

    @Test
    fun `when getFundingSources and false refresh and saved data then service doesn't gets called`() {
        mockGetFundingService()
        val balance: Balance = mock()
        val balanceEntity: BalanceLocalEntity = mock() {
            on { toBalance() } doReturn balance
        }
        whenever(balanceLocalDao.getBalances()).thenReturn(listOf(balanceEntity))

        val result = sut.getFundingSources(accountId = ACCOUNT_ID, refresh = false, page = 0, rows = 10)

        verifyZeroInteractions(service)
        assertEquals(listOf(balance).right(), result)
    }

    @Test
    fun `when assignAchAccountToBalance service gets called`() {
        whenever(service.assignAchAccountToBalance(BALANCE_ID)).thenReturn(achAccountDetails.right())

        val result = sut.assignAchAccountToBalance(BALANCE_ID)

        verify(service).assignAchAccountToBalance(BALANCE_ID)
        result.shouldBeRightAndEqualTo(achAccountDetails)
    }

    @Test
    fun `when getAchAccountDetails service gets called`() {
        whenever(service.getAchAccountDetails(BALANCE_ID)).thenReturn(achAccountDetails.right())

        val result = sut.getAchAccountDetails(BALANCE_ID)

        verify(service).getAchAccountDetails(BALANCE_ID)
        result.shouldBeRightAndEqualTo(achAccountDetails)
    }

    private fun mockGetFundingService() {
        whenever(service.getFundingSources(eq(ACCOUNT_ID), any(), any())).thenReturn(emptyList<Balance>().right())
    }
}
