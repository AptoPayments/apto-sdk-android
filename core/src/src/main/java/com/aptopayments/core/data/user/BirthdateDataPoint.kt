package com.aptopayments.core.data.user

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.io.Serializable

data class BirthdateDataPoint(
    val birthdate: LocalDate = LocalDate.now(),
    override val verification: Verification? = null,
    override val verified: Boolean? = false,
    override val notSpecified: Boolean? = false
) : DataPoint(), Serializable {
    override fun getType() = Type.BIRTHDATE

    fun toStringRepresentation(): String? = birthdate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
}
