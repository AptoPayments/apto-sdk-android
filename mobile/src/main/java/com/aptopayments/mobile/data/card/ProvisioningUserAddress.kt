package com.aptopayments.mobile.data.card

data class ProvisioningUserAddress(
    val name: String,
    val address1: String,
    val address2: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String,
    val phone: String
)
