package com.aptopayments.mobile.data.fundingsources

import java.io.Serializable

private const val LAST_DIGITS_QTY = 4

data class AchAccountDetails(val routingNumber: String, val accountNumber: String) : Serializable {

    fun accountLastDigits(): String {
        return if (accountNumber.length >= LAST_DIGITS_QTY) {
            accountNumber.takeLast(LAST_DIGITS_QTY)
        } else {
            accountNumber
        }
    }
}
