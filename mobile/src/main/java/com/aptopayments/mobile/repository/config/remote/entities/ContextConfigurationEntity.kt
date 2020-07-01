package com.aptopayments.mobile.repository.config.remote.entities

import com.aptopayments.mobile.data.config.ContextConfiguration
import com.google.gson.annotations.SerializedName

internal data class ContextConfigurationEntity(

    @SerializedName("project")
    val projectConfiguration: ProjectConfigurationEntity = ProjectConfigurationEntity()

) {
    fun toContextConfiguration() = ContextConfiguration(projectConfiguration.toProjectConfiguration())
}
