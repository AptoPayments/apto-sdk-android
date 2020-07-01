package com.aptopayments.mobile.repository.oauth.remote.entities

import com.aptopayments.mobile.data.workflowaction.AllowedBalanceType
import com.google.gson.annotations.SerializedName
import java.net.URL

internal data class AllowedBalanceTypeEntity(

    @SerializedName("balance_type")
    val type: String = "",

    @SerializedName("base_uri")
    val baseUrl: String
) {
    fun toAllowedBalanceType() = AllowedBalanceType(type, URL(baseUrl))

    companion object {
        fun from(allowedBalanceType: AllowedBalanceType): AllowedBalanceTypeEntity {
            return AllowedBalanceTypeEntity(
                type = allowedBalanceType.balanceType,
                baseUrl = allowedBalanceType.baseUri.toString()
            )
        }
    }
}
