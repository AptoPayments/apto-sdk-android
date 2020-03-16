package com.aptopayments.core.repository.card.remote.entities

import com.aptopayments.core.data.card.ProvisioningCardNetwork
import com.aptopayments.core.data.card.ProvisioningData
import com.aptopayments.core.data.card.ProvisioningTokenServiceProvider
import com.google.gson.annotations.SerializedName

data class ProvisioningDataEntity(
    @SerializedName("network")
    val network: String,
    @SerializedName("token_service_provider")
    val tokenServiceProvider: String,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("last_digits")
    val lastDigits: String,
    @SerializedName("user_address")
    val userAddress: ProvisioningUserAddressEntity,
    @SerializedName("opaque_payment_card")
    val opaquePaymentCard: String
) {
    fun toProvisioningData() =
        ProvisioningData(
            ProvisioningCardNetwork.fromString(network),
            ProvisioningTokenServiceProvider.fromString(tokenServiceProvider),
            displayName,
            lastDigits,
            userAddress.toProvisioningAddress(),
            opaquePaymentCard
        )
}
