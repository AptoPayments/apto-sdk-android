package com.aptopayments.mobile.repository.cardapplication.remote.entities

import com.aptopayments.mobile.data.card.SelectBalanceStoreResult
import com.google.gson.annotations.SerializedName
import java.util.Locale

internal data class SelectBalanceStoreResultEntity(

    @SerializedName("result")
    val result: String = "",

    @SerializedName("error_code")
    val errorCode: Int? = null

) {
    fun toSelectBalanceStoreResult() = SelectBalanceStoreResult(
        result = parseResult(result),
        errorCode = errorCode
    )

    private fun parseResult(result: String): SelectBalanceStoreResult.Type {
        return try {
            SelectBalanceStoreResult.Type.valueOf(result.uppercase(Locale.US))
        } catch (exception: IllegalArgumentException) {
            SelectBalanceStoreResult.Type.INVALID
        }
    }
}
