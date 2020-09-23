package com.aptopayments.mobile.data.card

import java.io.Serializable

data class FundingLimits(val daily: FundingSingleLimit) : Serializable
