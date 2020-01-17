package com.aptopayments.core.data.user

import com.aptopayments.core.data.user.DataPoint.Type.ID_DOCUMENT
import java.io.Serializable

data class IdDocumentDataPoint(
        override val verification: Verification? = null,
        override val verified: Boolean? = false,
        override val notSpecified: Boolean? = false,
        val type: Type? = null,
        val value: String? = null,
        val country: String? = null
) : DataPoint(), Serializable {
    override fun getType() = ID_DOCUMENT

    enum class Type { SSN, IDENTITY_CARD, PASSPORT, DRIVERS_LICENSE }
}
