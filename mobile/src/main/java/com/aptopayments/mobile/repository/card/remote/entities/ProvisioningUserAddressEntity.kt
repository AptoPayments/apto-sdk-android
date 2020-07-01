package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.ProvisioningUserAddress
import com.google.gson.annotations.SerializedName

internal data class ProvisioningUserAddressEntity(
    @SerializedName("name")
    val name: String?,
    @SerializedName("address1")
    val address1: String?,
    @SerializedName("address2")
    val address2: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("postal_code")
    val postalCode: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("phone")
    val phone: String?
) {
    fun toProvisioningAddress(): ProvisioningUserAddress {
        return ProvisioningUserAddress(
            name ?: "",
            address1 ?: "",
            address2 ?: "",
            city ?: "",
            state ?: "",
            postalCode ?: "",
            country ?: "",
            phone ?: ""
        )
    }
}
