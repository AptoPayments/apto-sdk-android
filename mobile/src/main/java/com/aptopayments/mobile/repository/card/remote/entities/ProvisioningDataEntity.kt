package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.data.card.ProvisioningData
import com.aptopayments.mobile.data.card.ProvisioningTokenServiceProvider
import com.google.gson.annotations.SerializedName

internal data class ProvisioningDataEntity(
    @SerializedName("card_network")
    val network: String,
    @SerializedName("token_service_provider")
    val tokenServiceProvider: String,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("last_four")
    val lastFour: String,
    @SerializedName("user_address")
    val userAddress: ProvisioningUserAddressEntity,
    @SerializedName("opaque_payment_card")
    val opaquePaymentCard: String
) {
    fun toProvisioningData() =
        ProvisioningData(
            Card.CardNetwork.fromString(network),
            ProvisioningTokenServiceProvider.fromString(tokenServiceProvider),
            displayName,
            lastFour,
            userAddress.toProvisioningAddress(),
            opaquePaymentCard
        )
}
