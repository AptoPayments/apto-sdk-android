package com.aptopayments.mobile.data.config

import com.aptopayments.mobile.data.geo.Country
import com.aptopayments.mobile.data.user.DataPoint
import java.io.Serializable

data class ProjectConfiguration(
    val name: String,
    val summary: String?,
    val branding: Branding,
    val labels: Map<String, String>,
    val allowedCountries: List<Country>,
    val supportEmailAddress: String?,
    val trackerAccessToken: String?,
    val isTrackerActive: Boolean?,
    val primaryAuthCredential: DataPoint.Type?,
    val secondaryAuthCredential: DataPoint.Type? = null,
    val requiredSignedPayloads: Boolean? = true
) : Serializable
