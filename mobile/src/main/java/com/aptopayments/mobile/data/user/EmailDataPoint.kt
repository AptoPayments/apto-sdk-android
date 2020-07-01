package com.aptopayments.mobile.data.user

import java.io.Serializable

data class EmailDataPoint(
    val email: String = "",
    override val verification: Verification? = null,
    override val verified: Boolean? = false,
    override val notSpecified: Boolean? = false
) : DataPoint(), Serializable {
    override fun getType() = Type.EMAIL
}
