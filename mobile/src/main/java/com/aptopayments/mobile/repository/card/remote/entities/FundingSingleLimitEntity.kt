package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.FundingSingleLimit
import com.google.gson.annotations.SerializedName

internal data class FundingSingleLimitEntity(
    @SerializedName("max")
    val max: MoneyEntity,

    @SerializedName("remaining")
    val remaining: MoneyEntity
) {
    fun toFundingSingleLimit() = FundingSingleLimit(max = max.toMoney(), remaining = remaining.toMoney())

    companion object {
        fun from(limit: FundingSingleLimit): FundingSingleLimitEntity {
            return FundingSingleLimitEntity(
                max = MoneyEntity.from(limit.max)!!,
                remaining = MoneyEntity.from(limit.remaining)!!
            )
        }
    }
}
