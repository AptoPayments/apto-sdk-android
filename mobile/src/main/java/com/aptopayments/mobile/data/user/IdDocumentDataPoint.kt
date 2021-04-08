package com.aptopayments.mobile.data.user

import com.aptopayments.mobile.data.user.DataPoint.Type.ID_DOCUMENT
import com.aptopayments.mobile.extension.localized
import java.io.Serializable
import java.util.Locale

data class IdDocumentDataPoint(
    val type: Type? = null,
    val value: String? = null,
    val country: String? = null,
    override val verification: Verification? = null,
    override val verified: Boolean? = false,
    override val notSpecified: Boolean? = false
) : DataPoint(), Serializable {
    override fun getType() = ID_DOCUMENT

    enum class Type {
        SSN, IDENTITY_CARD, PASSPORT, DRIVERS_LICENSE, UNKNOWN;

        companion object {
            fun fromString(value: String?) =
                try {
                    valueOf(value?.toUpperCase(Locale.US) ?: "")
                } catch (e: IllegalArgumentException) {
                    UNKNOWN
                }
        }

        fun toLocalizedString(): String {
            return when (this) {
                SSN -> "birthday_collector_id_document_type_ssn".localized()
                IDENTITY_CARD -> "birthday_collector_id_document_type_identity_card".localized()
                PASSPORT -> "birthday_collector_id_document_type_passport".localized()
                DRIVERS_LICENSE -> "birthday_collector_id_document_type_drivers_license".localized()
                UNKNOWN -> ""
            }
        }
    }
}
