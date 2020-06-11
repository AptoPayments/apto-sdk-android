package com.aptopayments.core.repository.transaction.remote.entities

import com.google.gson.annotations.SerializedName

internal data class GeoLocationEntity(

    @SerializedName("latitude")
    val latitude: Double?,

    @SerializedName("longitude")
    val longitude: Double?
)
