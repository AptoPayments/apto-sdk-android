package com.aptopayments.mobile.repository.p2p.remote.entities

import com.aptopayments.mobile.data.transfermoney.CardHolderData
import com.google.gson.annotations.SerializedName

internal data class CardholderDataEntity(
    @SerializedName("name")
    val name: CardHolderNameEntity,

    @SerializedName("cardholder_id")
    val cardholderId: String
) {
    fun toModelObject() =
        CardHolderData(name = name.toModelObject(), cardholderId)
}
