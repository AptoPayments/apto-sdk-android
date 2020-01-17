package com.aptopayments.core.data.user

import java.io.Serializable

data class EmailDataPoint (
        override val verification: Verification? = null,
        override val verified: Boolean? = false,
        override val notSpecified: Boolean? = false,
        val email: String = ""
) : DataPoint(), Serializable {
    override fun getType() = Type.EMAIL
}
