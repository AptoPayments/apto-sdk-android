package com.aptopayments.core.repository.user.remote.requests

import com.aptopayments.core.data.user.*
import com.aptopayments.core.network.ListEntity
import com.aptopayments.core.repository.user.remote.entities.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class UserDataRequest(

        @SerializedName("data_points")
        val userData: ListEntity<DataPointEntity>

) : Serializable {
    companion object {
        fun from(dataPointList: DataPointList): UserDataRequest =
                UserDataRequest(serializeDataPointList(dataPointList))

        fun serializeDataPointList(dataPointList: DataPointList): ListEntity<DataPointEntity> {
            val entities = dataPointList.getAllDataPoints()?.map {
                when (it.getType()) {
                    DataPoint.Type.NAME -> NameDataPointEntity.from(it as NameDataPoint)
                    DataPoint.Type.EMAIL -> EmailDataPointEntity.from(it as EmailDataPoint)
                    DataPoint.Type.PHONE -> PhoneDataPointEntity.from(it as PhoneDataPoint)
                    DataPoint.Type.ADDRESS -> AddressDataPointEntity.from(it as AddressDataPoint)
                    DataPoint.Type.BIRTHDATE -> BirthdateDataPointEntity.from(it as BirthdateDataPoint)
                    DataPoint.Type.ID_DOCUMENT -> IdDocumentDataPointEntity.from(it as IdDocumentDataPoint)
                }
            }
            return ListEntity(
                    type = "list",
                    page = 0,
                    totalCount = entities?.size ?: 0,
                    rows = entities?.size ?: 0,
                    data = entities
            )
        }
    }
}
