package com.aptopayments.core.data.card

import java.io.Serializable

data class CardDetails(
    val pan: String,
    val cvv: String,
    val expirationDate: String
) : Serializable {
    val expirationYear: Int
        get() = calcExpirationYear()

    val expirationMonth: Int
        get() = calcExpirationMonth()

    private fun calcExpirationYear(): Int {
        val components = expirationDate.split("-")
        return components.first().toInt().let { year ->
            if (year > 2000) year - 2000 else year
        }
    }

    private fun calcExpirationMonth() = expirationDate.split("-").last().toInt()

}
