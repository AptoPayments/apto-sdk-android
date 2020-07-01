package com.aptopayments.mobile.repository.transaction.remote.entities

import com.aptopayments.mobile.data.transaction.Merchant
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class MerchantEntity(

    @SerializedName("id")
    val id: String?,

    @SerializedName("key")
    val merchantKey: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("mcc")
    val mcc: MCCEntity?

) : Serializable {

    fun toMerchant() = Merchant(
        id = id,
        merchantKey = merchantKey,
        name = name,
        mcc = mcc?.toMCC()
    )

    companion object {
        fun from(merchant: Merchant?): MerchantEntity? {
            return merchant?.let {
                MerchantEntity(
                    id = it.id,
                    merchantKey = it.merchantKey,
                    name = it.name,
                    mcc = MCCEntity.from(it.mcc)
                )
            }
        }
    }
}
