package com.aptopayments.core.repository.config.remote.entities

import com.aptopayments.core.data.config.TeamConfiguration
import com.google.gson.annotations.SerializedName

internal data class TeamConfigurationEntity(

        @SerializedName("name")
        val name: String = "",

        @SerializedName("logo_url")
        val logoUrl: String? = null

) {
    fun toTeamConfiguration() = TeamConfiguration(
            name = name,
            logoUrl = logoUrl
    )
}
