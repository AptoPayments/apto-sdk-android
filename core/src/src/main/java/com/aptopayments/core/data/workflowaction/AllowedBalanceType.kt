package com.aptopayments.core.data.workflowaction

import java.io.Serializable
import java.net.URL

enum class BalanceType { COINBASE, UPHOLD }

data class AllowedBalanceType(
        var balanceType: BalanceType,
        var baseUri: URL
) : Serializable
