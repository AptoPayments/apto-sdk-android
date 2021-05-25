package com.aptopayments.mobile.data.statements

import org.threeten.bp.ZonedDateTime

/**
 * MonthlyStatement
 *
 * @property id String containing the id of the MonthlyStatement
 * @property month Int, month of the actual monthly statement, starting with 1 for January
 * @property year Int, year of the actual monthly statement
 * @property downloadUrl download URL of a PDF containing the Monthly Statement
 * @property urlExpiration ZonedDateTime of the expiration time that the link will be valid
 */
data class MonthlyStatement(
    val id: String,
    val month: Int,
    val year: Int,
    val downloadUrl: String?,
    val urlExpiration: ZonedDateTime?
) {
    fun canDownload() = downloadUrl != null && ZonedDateTime.now().isBefore(urlExpiration)
}
