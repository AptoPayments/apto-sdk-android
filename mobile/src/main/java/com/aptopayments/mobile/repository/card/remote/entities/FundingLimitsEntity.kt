package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.FundingLimits
import com.google.gson.annotations.SerializedName

internal data class FundingLimitsEntity(
    @SerializedName("daily")
    val daily: FundingSingleLimitEntity
) {
    fun toFundingLimits() =
        FundingLimits(daily = daily.toFundingSingleLimit())

    companion object {
        fun from(limits: FundingLimits): FundingLimitsEntity {
            return FundingLimitsEntity(
                FundingSingleLimitEntity.from(limits.daily)
            )
        }
    }
}
