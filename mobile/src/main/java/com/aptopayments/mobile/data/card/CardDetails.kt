package com.aptopayments.mobile.data.card

import java.io.Serializable

data class CardDetails(
    val pan: String,
    val cvv: String,
    val expirationDate: String
) : Serializable {

    val expirationYear: String
        get() = calcExpirationYear()

    val expirationMonth: String
        get() = calcExpirationMonth()

    private fun inTwoDigitsString(value: Int) = String.format("%02d", value)

    private fun calcExpirationYear(): String {
        return try {
            val components = expirationDate.split("-")
            val year = components.first().toInt().let { year ->
                if (year > 2000) year - 2000 else year
            }
            inTwoDigitsString(year)
        } catch (e: Exception) {
            ""
        }
    }

    private fun calcExpirationMonth(): String {
        return try {
            val year = expirationDate.split("-")[1].toInt()
            inTwoDigitsString(year)
        } catch (e: Exception) {
            ""
        }
    }
}
