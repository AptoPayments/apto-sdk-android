package com.aptopayments.core.data.card

import java.io.Serializable
import java.math.BigDecimal
import java.math.MathContext
import java.text.DecimalFormatSymbols
import java.util.*

private const val MINIMUM_DECIMAL_PLACES = 2

data class Money (
        val currency: String?,
        val amount: Double?
) : Serializable
{
    override fun toString(): String {
        return if (amount != null) formattedString(amount) else ""
    }

    fun toAbsString(): String {
        return if (amount != null) formattedString(Math.abs(amount)) else ""
    }

    private fun formattedString(amount: Double): String {
        val absAmount = Math.abs(amount)
        val bigDecimal = twoDecimalFiguresRounded(absAmount)
        val currencySymbol = currencySymbol()
        var format = "%s%f"
        if (amount < 0) format = "-$format"
        return decimalPlacesAdjustment(String.format(format, currencySymbol, bigDecimal.toDouble()))
    }

    private fun twoDecimalFiguresRounded(number: Double): BigDecimal {
        var precision = 2
        var intNumber = number.toInt()
        while (intNumber > 0) {
            precision++
            intNumber /= 10
        }
        return number.toBigDecimal(MathContext(precision))
    }

    fun currencySymbol(): String {
        if (currency == null) return ""
        return try {
            Currency.getInstance(currency).symbol
        } catch (exception: IllegalArgumentException) {
            currency
        }
    }

    private fun decimalPlacesAdjustment(string: String): String {
        val trimmed = string.trim('0')
        var decimals = trimmed.lastIndex - trimmed.lastIndexOf(DecimalFormatSymbols.getInstance().decimalSeparator)
        var adjustedString = trimmed
        while (decimals < MINIMUM_DECIMAL_PLACES) {
            adjustedString += "0"
            decimals++
        }
        return adjustedString
    }
}
