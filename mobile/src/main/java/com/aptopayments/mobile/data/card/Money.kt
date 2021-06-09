package com.aptopayments.mobile.data.card

import java.io.Serializable
import java.math.BigDecimal
import java.math.MathContext
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.abs

private const val MINIMUM_DECIMAL_PLACES = 2

private const val EUR_SYMBOL = "€"
private const val POUND_SYMBOL = "£"
private const val US_DOLLAR_SYMBOL = "$"

private const val EUR_ISO_CODE = "EUR"
private const val POUND_ISO_CODE = "GBP"
private const val US_DOLLAR_ISO_CODE = "USD"

private const val US_COUNTRY_2_ISO_CODE = "US"

data class Money(
    val currency: String?,
    val amount: Double?
) : Serializable {
    override fun toString(): String = amount?.let { formattedString(amount) } ?: ""

    fun toAbsString() = amount?.let { formattedString(abs(amount)) } ?: ""

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

    fun currencySymbol(): String = CurrencySymbolProvider().provide(currency)

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

internal class CurrencySymbolProvider {
    fun provide(currency: String?): String {
        return currency?.let {
            getKnownCurrencySymbol(it) ?: getSystemCurrencySymbol(it)
        } ?: ""
    }

    private fun getSystemCurrencySymbol(currency: String): String {
        return try {
            Currency.getInstance(currency).symbol
        } catch (exception: IllegalArgumentException) {
            currency
        }
    }

    private fun getKnownCurrencySymbol(currency: String?): String? {
        return when {
            currency == EUR_ISO_CODE -> EUR_SYMBOL
            currency == POUND_ISO_CODE -> POUND_SYMBOL
            currency == US_DOLLAR_ISO_CODE && Locale.getDefault().country == US_COUNTRY_2_ISO_CODE -> US_DOLLAR_SYMBOL
            else -> null
        }
    }
}
