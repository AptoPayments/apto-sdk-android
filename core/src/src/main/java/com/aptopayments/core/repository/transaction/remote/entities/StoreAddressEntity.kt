package com.aptopayments.core.repository.transaction.remote.entities

import com.aptopayments.core.data.geo.Country
import com.aptopayments.core.data.transaction.StoreAddress
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class StoreAddressEntity(

    @SerializedName("street_one")
    val address: String?,

    @SerializedName("street_two")
    val apUnit: String?,

    @SerializedName("country")
    val country: String?,

    @SerializedName("locality")
    val city: String?,

    @SerializedName("region")
    val region: String?,

    @SerializedName("postal_code")
    val zip: String?

) : Serializable {

    fun toStoreAddress() = StoreAddress(
        address = address,
        apUnit = apUnit,
        country = parseCountry(country),
        city = city,
        region = region,
        zip = zip
    )

    private fun parseCountry(country: String?): Country? {
        return country?.let {
            Country(isoCode = country)
        }
    }

    companion object {
        fun from(storeAddress: StoreAddress?): StoreAddressEntity? {
            return storeAddress?.let {
                StoreAddressEntity(
                    address = it.address,
                    apUnit = it.apUnit,
                    country = it.country?.isoCode,
                    city = it.city,
                    region = it.region,
                    zip = it.zip
                )
            }
        }
    }
}
