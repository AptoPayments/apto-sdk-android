package com.aptopayments.mobile.data.card

import java.io.Serializable

data class FundingSingleLimit(
    val max: Money,
    val remaining: Money
) : Serializable
