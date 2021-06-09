package com.aptopayments.mobile.data.transaction

import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.data.transaction.Transaction.TransactionDeviceType

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TransactionTest {

    @Test
    fun `given normal transaction when deviceType then OTHER`() {
        val sut = TestDataProvider.provideTransaction()

        assertEquals(TransactionDeviceType.OTHER, sut.deviceType())
    }

    @Test
    fun `given ecommerce transaction when deviceType then Ecommerce`() {
        val sut = TestDataProvider.provideTransaction(ecommerce = true)

        assertEquals(TransactionDeviceType.ECOMMERCE, sut.deviceType())
    }

    @Test
    fun `given cardPresent transaction when deviceType then CARDPRESENT`() {
        val sut = TestDataProvider.provideTransaction(cardPresent = true)

        assertEquals(TransactionDeviceType.CARDPRESENT, sut.deviceType())
    }

    @Test
    fun `given international transaction when deviceType then INTERNATIONAL`() {
        val sut = TestDataProvider.provideTransaction(international = true)

        assertEquals(TransactionDeviceType.INTERNATIONAL, sut.deviceType())
    }

    @Test
    fun `given emv transaction when deviceType then EMV`() {
        val sut = TestDataProvider.provideTransaction(emv = true)

        assertEquals(TransactionDeviceType.EMV, sut.deviceType())
    }
}
