package com.aptopayments.mobile.data.user

import java.io.Serializable

/**
 * @property streetOne Address
 * @property streetTwo Apartment
 * @property locality City
 * @property region Province / State
 * @property postalCode
 * @property country Country code in standard ISO_3166-1 alpha-2
 */
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
