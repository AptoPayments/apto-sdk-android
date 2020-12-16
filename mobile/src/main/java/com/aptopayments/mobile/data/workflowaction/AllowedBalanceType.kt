package com.aptopayments.mobile.data.workflowaction

import java.io.Serializable
import java.net.URL

data class AllowedBalanceType(
    val balanceType: String,
    val baseUri: URL
) : Serializable
