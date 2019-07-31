package com.aptopayments.core.data.config

import java.io.Serializable

data class TeamConfiguration (
        val name: String,
        val logoUrl: String?
) : Serializable
