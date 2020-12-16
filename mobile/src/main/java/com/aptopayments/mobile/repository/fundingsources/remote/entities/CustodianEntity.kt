package com.aptopayments.mobile.repository.fundingsources.remote.entities

import com.aptopayments.mobile.data.card.Custodian
import com.google.gson.annotations.SerializedName

internal class CustodianEntity(

    @SerializedName("name")
    val name: String = "",

    @SerializedName("logo")
    val logo: String = "",

    @SerializedName("custodian_type")
    val type: String = "",

    @SerializedName("id")
    val id: String = ""
) {
    fun toCustodian() = Custodian(
        name = name,
        logo = logo,
        type = type,
        id = id
    )
}
