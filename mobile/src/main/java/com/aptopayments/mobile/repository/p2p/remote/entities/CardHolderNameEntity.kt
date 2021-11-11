package com.aptopayments.mobile.repository.p2p.remote.entities

import com.aptopayments.mobile.data.transfermoney.CardHolderName
import com.google.gson.annotations.SerializedName

internal data class CardHolderNameEntity(
    @SerializedName("first_name")
    val firstName: String? = "",
    @SerializedName("last_name")
    val lastName: String? = "",
) {
    fun toModelObject(): CardHolderName {
        return CardHolderName(firstName = firstName ?: "", lastName = lastName ?: "")
    }
}
