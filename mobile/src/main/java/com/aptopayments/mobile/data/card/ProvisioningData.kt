package com.aptopayments.mobile.data.card

data class ProvisioningData(
    val network: ProvisioningCardNetwork,
    val tokenServiceProvider: ProvisioningTokenServiceProvider,
    val displayName: String,
    val lastDigits: String,
    val userAddress: ProvisioningUserAddress,
    val opaquePaymentCard: String
)
