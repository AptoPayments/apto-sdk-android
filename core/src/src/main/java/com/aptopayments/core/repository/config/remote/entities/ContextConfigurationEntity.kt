package com.aptopayments.core.repository.config.remote.entities

import com.aptopayments.core.data.config.ContextConfiguration
import com.google.gson.annotations.SerializedName

internal data class ContextConfigurationEntity(

    @SerializedName("team")
    val teamConfiguration: TeamConfigurationEntity = TeamConfigurationEntity(),

    @SerializedName("project")
    val projectConfiguration: ProjectConfigurationEntity = ProjectConfigurationEntity()

) {
    fun toContextConfiguration() = ContextConfiguration(
        teamConfiguration = teamConfiguration.toTeamConfiguration(),
        projectConfiguration = projectConfiguration.toProjectConfiguration()
    )
}
