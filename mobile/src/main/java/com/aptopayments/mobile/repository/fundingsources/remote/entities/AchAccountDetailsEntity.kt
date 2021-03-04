package com.aptopayments.mobile.repository.fundingsources.remote.entities

import com.aptopayments.mobile.data.fundingsources.AchAccountDetails
import com.google.gson.annotations.SerializedName

internal data class AchAccountDetailsEntity(
    @SerializedName("routing_number")
    val routingNumber: String? = "",
    @SerializedName("account_number")
    val accountNumber: String? = ""
) {
    fun toAchAccountDetails(): AchAccountDetails {
        return AchAccountDetails(routingNumber = routingNumber ?: "", accountNumber = accountNumber ?: "")
    }

    companion object {
        fun fromAchAccountDetails(value: AchAccountDetails?): AchAccountDetailsEntity? {
            return value?.let {
                AchAccountDetailsEntity(
                    routingNumber = value.routingNumber,
                    accountNumber = value.accountNumber
                )
            }
        }
    }
}
