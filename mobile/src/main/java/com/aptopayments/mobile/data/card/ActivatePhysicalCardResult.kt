package com.aptopayments.mobile.data.card

import java.io.Serializable

enum class ActivatePhysicalCardResultType { ACTIVATED, ERROR }

data class ActivatePhysicalCardResult(
    val result: ActivatePhysicalCardResultType,
    val errorCode: String? = null,
    val errorMessage: String? = null
) : Serializable
