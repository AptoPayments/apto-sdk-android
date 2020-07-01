package com.aptopayments.mobile.repository.user.remote.entities

import com.aptopayments.mobile.network.ListEntity
import com.google.gson.annotations.SerializedName

internal class DataPointGroupEntity {
    @SerializedName("type")
    val type: String? = ""

    @SerializedName("datapoint_group_id")
    val datapointGroupId: String? = ""

    @SerializedName("datapoint_group_type")
    val datapointGroupType: String = ""

    @SerializedName("order")
    val order: Int = 0

    @SerializedName("datapoints")
    var datapoints: ListEntity<RequiredDataPointEntity> = ListEntity()
}
