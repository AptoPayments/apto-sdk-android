package com.aptopayments.core.repository.user.remote.entities

import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.data.user.User
import com.aptopayments.core.network.ListEntity
import com.google.gson.annotations.SerializedName

internal data class UserEntity(

        @SerializedName("user_id")
        val userId: String = "",

        @SerializedName("user_token")
        val userToken: String = "",

        @SerializedName("user_data")
        val userData: ListEntity<DataPointEntity>? = null
) {
    fun toUser() = User (
            userId = userId,
            userData = if (userData?.data?.isEmpty() == false) DataPointList(userData.data?.map { it.toDataPoint() }) else null,
            token = userToken
    )
}
