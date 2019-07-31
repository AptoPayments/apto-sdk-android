package com.aptopayments.core.repository.transaction.remote.entities

import com.aptopayments.core.data.transaction.Store
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class StoreEntity (

        @SerializedName("id")
        val id: String?,

        @SerializedName("key")
        val storeKey: String?,

        @SerializedName("name")
        val name: String?,

        @SerializedName("location")
        val geoLocation: GeoLocationEntity?,

        @SerializedName("address")
        val address: StoreAddressEntity?,

        @SerializedName("merchant")
        val merchant: MerchantEntity?

) : Serializable {

    fun toStore() = Store(
            id = id,
            storeKey = storeKey,
            name = name,
            latitude = geoLocation?.latitude,
            longitude = geoLocation?.longitude,
            address = address?.toStoreAddress(),
            merchant = merchant?.toMerchant()
    )

    companion object {
        fun from(store: Store?): StoreEntity? {
            return store?.let {
                StoreEntity(
                        id = it.id,
                        storeKey = it.storeKey,
                        name = it.name,
                        geoLocation = GeoLocationEntity(latitude = it.latitude, longitude = it.longitude),
                        address = StoreAddressEntity.from(it.address),
                        merchant = MerchantEntity.from(it.merchant)
                )
            }
        }
    }
}
