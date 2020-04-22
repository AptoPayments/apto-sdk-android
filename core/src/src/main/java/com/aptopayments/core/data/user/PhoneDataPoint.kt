package com.aptopayments.core.data.user

import com.aptopayments.core.data.PhoneNumber
import java.io.Serializable

data class PhoneDataPoint(
    val phoneNumber: PhoneNumber = PhoneNumber("", ""),
    override val verification: Verification? = null,
    override val verified: Boolean? = false,
    override val notSpecified: Boolean? = false
) : DataPoint(), Serializable {
    override fun getType() = Type.PHONE
    fun toStringRepresentation() = phoneNumber.toStringRepresentation()
}
