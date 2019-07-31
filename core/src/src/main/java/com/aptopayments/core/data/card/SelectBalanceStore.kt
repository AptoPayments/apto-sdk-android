package com.aptopayments.core.data.card

import com.aptopayments.core.data.workflowaction.AllowedBalanceType
import java.io.Serializable

data class SelectBalanceStore(
        val allowedBalanceTypes: List<AllowedBalanceType>?
) : Serializable
