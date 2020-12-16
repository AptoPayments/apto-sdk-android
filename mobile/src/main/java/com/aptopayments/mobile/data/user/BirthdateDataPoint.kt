package com.aptopayments.mobile.data.user

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.io.Serializable
import java.util.*

data class BirthdateDataPoint(
    val birthdate: LocalDate = LocalDate.now(),
    override val verification: Verification? = null,
    override val verified: Boolean? = false,
    override val notSpecified: Boolean? = false
) : DataPoint(), Serializable {
    override fun getType() = Type.BIRTHDATE

    fun toStringRepresentation(locale: Locale = Locale.getDefault()): String? =
        birthdate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(locale))
}
