package com.aptopayments.core.repository.card.remote.entities

import com.aptopayments.core.data.card.ActivatePhysicalCardResult
import com.aptopayments.core.data.card.ActivatePhysicalCardResultType
import com.google.gson.annotations.SerializedName

internal data class ActivatePhysicalCardEntity (

        @SerializedName("result")
        val result: String = "",

        @SerializedName("error_code")
        val errorCode: String = "",

        @SerializedName("error_message")
        val errorMessage: String = ""
) {
    fun toActivatePhysicalCardResult() = ActivatePhysicalCardResult(
            result = parseResult(result),
            errorCode = errorCode,
            errorMessage = errorMessage
    )

    private fun parseResult(result: String): ActivatePhysicalCardResultType {
        return try {
            ActivatePhysicalCardResultType.valueOf(result.toUpperCase())
        } catch (exception: Throwable) {
            ActivatePhysicalCardResultType.ERROR
        }
    }
}
