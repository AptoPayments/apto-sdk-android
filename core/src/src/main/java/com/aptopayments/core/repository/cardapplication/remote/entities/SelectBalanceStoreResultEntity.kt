package com.aptopayments.core.repository.cardapplication.remote.entities

import com.aptopayments.core.data.card.SelectBalanceStoreResult
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
            SelectBalanceStoreResult.Type.valueOf(result.toUpperCase(Locale.US))
        } catch (exception: Throwable) {
            SelectBalanceStoreResult.Type.INVALID
        }
    }
}
