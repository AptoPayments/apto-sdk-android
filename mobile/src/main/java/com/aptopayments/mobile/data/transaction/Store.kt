package com.aptopayments.mobile.data.transaction

import java.io.Serializable

data class Store(
    val id: String?,
    val storeKey: String?,
    val name: String?,
    val latitude: Double?,
    val longitude: Double?,
    val address: StoreAddress?,
    val merchant: Merchant?
) : Serializable
