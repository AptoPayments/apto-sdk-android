package com.aptopayments.core.data.user

import com.aptopayments.core.data.PhoneNumber
import java.io.Serializable

data class PhoneDataPoint (
        override val verification: Verification? = null,
        override val verified: Boolean? = false,
        override val notSpecified: Boolean? = false,
        val phoneNumber: PhoneNumber = PhoneNumber("", "")
) : DataPoint(), Serializable {
    override fun getType() = Type.PHONE
    fun toStringRepresentation() = phoneNumber.toStringRepresentation()
}
