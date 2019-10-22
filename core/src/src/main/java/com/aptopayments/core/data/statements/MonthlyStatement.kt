package com.aptopayments.core.data.statements

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

data class MonthlyStatement(
    val id: String,
    val month: Int,
    val year: Int,
    val downloadUrl: String?,
    val urlExpiration: LocalDateTime?
) {
    fun canDownload() = downloadUrl != null && LocalDateTime.now().isBefore(urlExpiration)

    fun getLocalDate(): LocalDate = LocalDate.of(year, month, 1)
}
