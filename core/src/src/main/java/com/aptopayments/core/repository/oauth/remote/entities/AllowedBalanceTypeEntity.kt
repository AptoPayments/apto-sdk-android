package com.aptopayments.core.repository.oauth.remote.entities

import com.aptopayments.core.data.workflowaction.AllowedBalanceType
import com.aptopayments.core.data.workflowaction.BalanceType
import com.google.gson.annotations.SerializedName
import java.net.URL
import java.util.Locale

internal data class AllowedBalanceTypeEntity(

        @SerializedName("balance_type")
        val type: String = "",

        @SerializedName("base_uri")
        val baseUrl : String
) {
    fun toAllowedBalanceType() = AllowedBalanceType(parseBalanceType(type), URL(baseUrl))

    private fun parseBalanceType(type: String): BalanceType {
        return try {
            BalanceType.valueOf(type.toUpperCase(Locale.US))
        } catch (exception: Throwable) {
            BalanceType.COINBASE
        }
    }

    companion object {
        fun from (allowedBalanceType: AllowedBalanceType): AllowedBalanceTypeEntity {
            return AllowedBalanceTypeEntity(
                    type = allowedBalanceType.balanceType.toString(),
                    baseUrl = allowedBalanceType.baseUri.toString()
            )
        }
    }
}
