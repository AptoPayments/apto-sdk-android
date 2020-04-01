package com.aptopayments.core.repository.user.remote.entities

import com.aptopayments.core.data.geo.Country
import com.aptopayments.core.data.user.AddressDataPointConfiguration
import com.google.gson.annotations.SerializedName

internal class AddressDataPointConfigurationEntity : DataPointConfigurationEntity() {

    @SerializedName("allowed_countries")
    val allowedCountries: List<String> = listOf()

    override fun toDataPointConfiguration() = AddressDataPointConfiguration(allowedCountries.map { Country(it) })
}
