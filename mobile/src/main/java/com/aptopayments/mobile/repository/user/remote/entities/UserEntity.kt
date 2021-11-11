package com.aptopayments.mobile.repository.user.remote.entities

import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.data.user.User
import com.aptopayments.mobile.network.ListEntity
import com.google.gson.annotations.SerializedName

internal data class UserEntity(

    @SerializedName("user_id")
    val userId: String = "",

    @SerializedName("user_token")
    val userToken: String = "",

    @SerializedName("user_data")
    val userData: ListEntity<DataPointEntity>? = null
) {
    fun toUser() = User(
        userId = userId,
        userData = getUserData(userData),
        token = userToken
    )

    private fun getUserData(userData: ListEntity<DataPointEntity>?) =
        if (userData?.data?.isEmpty() == false) DataPointList(userData.data.map { it.toDataPoint() }) else null
}
