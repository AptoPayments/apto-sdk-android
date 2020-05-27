package com.aptopayments.core.repository.user.remote.entities

import com.aptopayments.core.data.user.DataPointConfiguration
import com.google.gson.annotations.SerializedName

internal abstract class DataPointConfigurationEntity {
    @SerializedName("type")
    val type: String = ""

    abstract fun toDataPointConfiguration(): DataPointConfiguration
}
