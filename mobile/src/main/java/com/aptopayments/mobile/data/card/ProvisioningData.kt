package com.aptopayments.mobile.data.card

data class ProvisioningData(
    val network: Card.CardNetwork,
    val tokenServiceProvider: ProvisioningTokenServiceProvider,
    val displayName: String,
    val lastFour: String,
    val userAddress: ProvisioningUserAddress,
    val opaquePaymentCard: String
)
