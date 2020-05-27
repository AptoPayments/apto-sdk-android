package com.aptopayments.core.data.config

import com.aptopayments.core.data.geo.Country
import com.aptopayments.core.data.user.DataPoint
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
    val secondaryAuthCredential: DataPoint.Type? = null
) : Serializable
