package com.aptopayments.mobile.data.card

import com.aptopayments.mobile.data.workflowaction.AllowedBalanceType
import java.io.Serializable

data class SelectBalanceStore(
    val allowedBalanceTypes: List<AllowedBalanceType>?
) : Serializable
