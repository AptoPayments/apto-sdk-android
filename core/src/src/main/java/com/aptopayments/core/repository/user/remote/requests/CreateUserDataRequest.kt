package com.aptopayments.core.repository.user.remote.requests

import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.network.ListEntity
import com.aptopayments.core.repository.user.remote.entities.DataPointEntity
import com.aptopayments.core.repository.user.remote.requests.UserDataRequest.Companion.serializeDataPointList
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class CreateUserDataRequest(
    @SerializedName("data_points")
    val userData: ListEntity<DataPointEntity>,

    @SerializedName("custodian_uid")
    val custodianUid: String? = null
) : Serializable {
    companion object {
        fun from(dataPointList: DataPointList, custodianUid: String?): CreateUserDataRequest =
            CreateUserDataRequest(serializeDataPointList(dataPointList), custodianUid)
    }
}
