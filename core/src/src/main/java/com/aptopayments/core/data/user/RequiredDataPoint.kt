package com.aptopayments.core.data.user

import java.io.Serializable

internal data class RequiredDataPoint(
    val type: DataPoint.Type?,
    val notSpecifiedAllowed: Boolean,
    val datapointConfiguration: DataPointConfiguration?
) : Serializable