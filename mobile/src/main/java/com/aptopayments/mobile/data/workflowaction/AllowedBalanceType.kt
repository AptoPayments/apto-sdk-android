package com.aptopayments.mobile.data.workflowaction

import java.io.Serializable
import java.net.URL

data class AllowedBalanceType(
    var balanceType: String,
    var baseUri: URL
) : Serializable
