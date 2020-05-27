package com.aptopayments.core.data.card

import java.io.Serializable

enum class ActivatePhysicalCardResultType { ACTIVATED, ERROR }

data class ActivatePhysicalCardResult(

    val result: ActivatePhysicalCardResultType,
    val errorCode: String?,
    val errorMessage: String?

) : Serializable
