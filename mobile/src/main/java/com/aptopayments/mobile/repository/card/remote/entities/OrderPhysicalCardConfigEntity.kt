package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.OrderPhysicalCardConfig
import com.aptopayments.mobile.repository.user.remote.entities.AddressDataPointEntity
import com.google.gson.annotations.SerializedName

internal data class OrderPhysicalCardConfigEntity(
    @SerializedName("issuance_fee")
    val issuanceFee: MoneyEntity,

    @SerializedName("user_address")
    val userAddress: AddressDataPointEntity? = null
) {
    fun toOrderPhysicalCardConfig() = OrderPhysicalCardConfig(
        issuanceFee = issuanceFee.toMoney(),
        userAddress = userAddress?.toDataPoint()
    )
}
