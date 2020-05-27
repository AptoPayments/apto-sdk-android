package com.aptopayments.core.data.transaction

import java.io.Serializable

data class Merchant(
    val id: String?,
    val merchantKey: String?,
    val name: String?,
    val mcc: MCC?
) : Serializable
