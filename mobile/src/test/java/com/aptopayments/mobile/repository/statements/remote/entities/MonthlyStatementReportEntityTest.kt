package com.aptopayments.mobile.repository.statements.remote.entities

import org.junit.Test
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import kotlin.test.assertEquals

class MonthlyStatementReportEntityTest {

    @Test
    fun `when no arguments given then MonthlyStatementReport generated without errors`() {
        MonthlyStatementReportEntity().toMonthlyStatementReport()
    }

    @Test
    fun `when arguments given then MonthlyStatementReport generated correctly`() {
        val month = 10
        val year = 2019
        val id = "ID"
        val downloadUrl = "http://www.example.com"
        val urlExpiration = "2019-10-15T12:13:19.195312+00:00"
        val expirationDate = ZonedDateTime.of(2019, 10, 15, 12, 13, 19, 195312000, ZoneOffset.UTC)

        val report = MonthlyStatementReportEntity(
            month,
            year,
            id,
            downloadUrl,
            urlExpiration
        ).toMonthlyStatementReport()

        assertEquals(month, report.month)
        assertEquals(year, report.year)
        assertEquals(id, report.id)
        assertEquals(downloadUrl, report.downloadUrl)
        assertEquals(expirationDate, report.urlExpiration)
    }
}
