package com.aptopayments.mobile.repository.config.remote.entities

import com.aptopayments.mobile.data.config.ProjectConfiguration
import com.aptopayments.mobile.data.geo.Country
import com.aptopayments.mobile.data.user.DataPoint
import com.google.gson.annotations.SerializedName

internal data class ProjectConfigurationEntity(

    @SerializedName("name")
    val name: String = "",

    @SerializedName("summary")
    val summary: String? = null,

    @SerializedName("branding")
    val brandingEntity: BrandingEntity = BrandingEntity(),

    @SerializedName("primary_auth_credential")
    val primaryOAuthCredential: String = "",

    @SerializedName("secondary_auth_credential")
    val secondaryOAuthCredential: String = "",

    @SerializedName("allowed_countries")
    val allowedCountries: List<String> = arrayListOf("US"),

    @SerializedName("labels")
    val labels: Map<String, String> = hashMapOf(),

    @SerializedName("support_source_address")
    val supportEmailAddress: String? = null,

    @SerializedName("tracker_access_token")
    val trackerAccessToken: String? = null,

    @SerializedName("tracker_active")
    val isTrackerActive: Boolean? = false,

    @SerializedName("required_signed_payloads")
    val requiredSignedPayloads: Boolean? = true,

) {
    fun toProjectConfiguration() = ProjectConfiguration(
        name = name,
        summary = summary,
        branding = brandingEntity.toBranding(),
        labels = labels,
        allowedCountries = allowedCountries.map { Country(it) },
        supportEmailAddress = supportEmailAddress,
        trackerAccessToken = trackerAccessToken,
        isTrackerActive = isTrackerActive,
        primaryAuthCredential = DataPoint.Type.fromString(primaryOAuthCredential),
        secondaryAuthCredential = DataPoint.Type.fromString(secondaryOAuthCredential),
        requiredSignedPayloads = requiredSignedPayloads
    )
}
