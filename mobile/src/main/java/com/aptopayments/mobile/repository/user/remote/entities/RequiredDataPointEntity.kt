package com.aptopayments.mobile.repository.user.remote.entities

import com.aptopayments.mobile.data.user.DataPoint
import com.aptopayments.mobile.data.user.RequiredDataPoint
import com.google.gson.annotations.SerializedName

internal class RequiredDataPointEntity {
    @SerializedName("datapoint_type")
    val dataPointType: String = ""

    @SerializedName("not_specified_allowed")
    var notSpecifiedAllowed = false

    @SerializedName("datapoint_configuration")
    var dataPointConfiguration: DataPointConfigurationEntity? = null

    fun toRequiredDataPoint() = RequiredDataPoint(
        DataPoint.Type.fromString(dataPointType),
        notSpecifiedAllowed,
        dataPointConfiguration?.toDataPointConfiguration()
    )
}
