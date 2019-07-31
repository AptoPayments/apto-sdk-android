package com.aptopayments.core.data.user

import java.io.Serializable

data class NameDataPoint (
        override val verification: Verification? = null,
        override val verified: Boolean? = false,
        override val notSpecified: Boolean? = false,
        val firstName: String = "",
        val lastName: String = ""
) : DataPoint(), Serializable {
    override fun getType() = Type.NAME
}
