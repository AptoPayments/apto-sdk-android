package com.aptopayments.core.data.config

import java.io.Serializable

data class ContextConfiguration(
    val teamConfiguration: TeamConfiguration,
    val projectConfiguration: ProjectConfiguration
) : Serializable
