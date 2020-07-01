package com.aptopayments.mobile.data.user

import java.io.Serializable
import java.util.Locale

abstract class DataPoint : Serializable {
    abstract val verification: Verification?
    abstract val verified: Boolean?
    abstract val notSpecified: Boolean?
    abstract fun getType(): Type

    enum class Type {
        NAME, PHONE, EMAIL, BIRTHDATE, ADDRESS, ID_DOCUMENT;

        companion object {
            fun fromString(string: String) =
                try {
                    valueOf(string.toUpperCase(Locale.US))
                } catch (e: Exception) {
                    null
                }
        }
    }
}
