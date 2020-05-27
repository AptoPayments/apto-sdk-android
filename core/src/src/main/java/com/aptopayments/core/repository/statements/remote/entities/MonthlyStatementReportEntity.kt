package com.aptopayments.core.repository.statements.remote.entities

import com.aptopayments.core.data.statements.MonthlyStatement
import com.google.gson.annotations.SerializedName
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

internal data class MonthlyStatementReportEntity(
    @SerializedName("month")
    val month: Int = 0,

    @SerializedName("year")
    val year: Int = 0,

    @SerializedName("id")
    val id: String = "",

    @SerializedName("download_url")
    val downloadUrl: String? = null,

    @SerializedName("url_expiration")
    val urlExpiration: String? = null
) {
    fun toMonthlyStatementReport() =
        MonthlyStatement(
            id,
            month,
            year,
            downloadUrl,
            getLocalDateTime(urlExpiration)
        )

    private fun getLocalDateTime(expiration: String?) =
        expiration?.let { ZonedDateTime.parse(expiration) }?.withZoneSameInstant(ZoneOffset.UTC)
}
