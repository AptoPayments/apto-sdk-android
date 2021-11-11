package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.ActivatePhysicalCardResult
import com.aptopayments.mobile.data.card.ActivatePhysicalCardResultType
import com.google.gson.annotations.SerializedName
import java.util.Locale

internal data class ActivatePhysicalCardEntity(

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
            ActivatePhysicalCardResultType.valueOf(result.uppercase(Locale.US))
        } catch (exception: IllegalArgumentException) {
            ActivatePhysicalCardResultType.ERROR
        }
    }
}
