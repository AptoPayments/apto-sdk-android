package com.aptopayments.core.data.user

import java.io.Serializable

data class NameDataPoint(
    val firstName: String = "",
    val lastName: String = "",
    override val verification: Verification? = null,
    override val verified: Boolean? = false,
    override val notSpecified: Boolean? = false
) : DataPoint(), Serializable {
    override fun getType() = Type.NAME
}
