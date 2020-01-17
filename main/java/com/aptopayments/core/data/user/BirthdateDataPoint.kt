package com.aptopayments.core.data.user

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class BirthdateDataPoint (
        override val verification: Verification? = null,
        override val verified: Boolean? = false,
        override val notSpecified: Boolean? = false,
        val birthdate: Date = Date()
) : DataPoint(), Serializable {
    override fun getType() = Type.BIRTHDATE
    fun toStringRepresentation(): String? = SimpleDateFormat.getDateInstance(
            SimpleDateFormat.LONG, Locale.getDefault()).format(birthdate)
}
