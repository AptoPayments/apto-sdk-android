package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.FundingLimits
import com.google.gson.annotations.SerializedName

internal data class FundingLimitsEntity(
    @SerializedName("daily")
    val daily: FundingSingleLimitEntity,

    @SerializedName("monthly")
    val monthly: FundingSingleLimitEntity
) {
    fun toFundingLimits() =
        FundingLimits(daily = daily.toFundingSingleLimit(), monthly = monthly.toFundingSingleLimit())

    companion object {
        fun from(limits: FundingLimits): FundingLimitsEntity {
            return FundingLimitsEntity(
                FundingSingleLimitEntity.from(limits.daily),
                FundingSingleLimitEntity.from(limits.monthly)
            )
        }
    }
}
