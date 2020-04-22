package com.aptopayments.core.repository.config.remote.entities

import com.aptopayments.core.data.config.ProjectConfiguration
import com.aptopayments.core.data.geo.Country
import com.aptopayments.core.data.user.DataPoint
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
        var supportEmailAddress: String? = null,

        @SerializedName("tracker_access_token")
        var trackerAccessToken: String? = null,

        @SerializedName("tracker_active")
        var isTrackerActive: Boolean? = false
        // TODO: Parse the welcome action

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
            secondaryAuthCredential = DataPoint.Type.fromString(secondaryOAuthCredential)
    )

}
