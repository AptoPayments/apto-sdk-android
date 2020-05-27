package com.aptopayments.core.data.user

import java.io.Serializable

data class AddressDataPoint(
    val streetOne: String? = null,
    val streetTwo: String? = null,
    val locality: String? = null,
    val region: String? = null,
    val postalCode: String? = null,
    val country: String? = null,
    override val verification: Verification? = null,
    override val verified: Boolean? = false,
    override val notSpecified: Boolean? = false
) : DataPoint(), Serializable {
    override fun getType() = Type.ADDRESS

    fun toStringRepresentation(): String {
        return arrayListOf(streetOne, streetTwo, locality, region, postalCode, country)
            .asSequence()
            .filterNotNull()
            .joinToString(", ")
    }
}
