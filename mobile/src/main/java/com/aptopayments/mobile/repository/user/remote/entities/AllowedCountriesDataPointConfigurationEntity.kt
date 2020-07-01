package com.aptopayments.mobile.repository.user.remote.entities

import com.aptopayments.mobile.data.geo.Country
import com.aptopayments.mobile.data.user.AllowedCountriesConfiguration
import com.google.gson.annotations.SerializedName

internal class AllowedCountriesDataPointConfigurationEntity : DataPointConfigurationEntity() {

    @SerializedName("allowed_countries")
    val allowedCountries: List<String> = listOf()

    override fun toDataPointConfiguration() = AllowedCountriesConfiguration(allowedCountries.map { Country(it) })
}
