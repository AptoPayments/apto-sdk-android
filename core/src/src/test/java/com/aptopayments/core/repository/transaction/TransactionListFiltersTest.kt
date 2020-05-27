package com.aptopayments.core.repository.transaction

import org.junit.Assert.*
import org.junit.Test
import org.threeten.bp.LocalDate

private const val PAGE = 3
private const val ROWS = 1
private const val LAST_TRANSACTION_ID = "asdasd"
private val START_DATE = LocalDate.of(2020, 5, 7)
private val END_DATE = LocalDate.of(2020, 6, 16)
private const val MCC_CODE = "mcc1"
private const val TYPE_1 = "1"

class TransactionListFiltersTest {

    @Test
    fun `when sut empty then toOptionsMap has no data`() {
        val sut = TransactionListFilters(rows = ROWS)

        val optionsMap = sut.toOptionsMap()

        assertEquals(1, optionsMap.size)
        assertEquals(ROWS.toString(), optionsMap["rows"])
    }

    @Test
    fun `when sut is fully qualified then toOptionsMap has all data`() {
        val sut = TransactionListFilters(
            page = PAGE, rows = ROWS, lastTransactionId = LAST_TRANSACTION_ID,
            startDate = START_DATE, endDate = END_DATE, mccCode = MCC_CODE, type = TYPE_1
        )

        val optionsMap = sut.toOptionsMap()

        assertEquals(7, optionsMap.size)
        assertEquals(PAGE.toString(), optionsMap["page"])
        assertEquals(ROWS.toString(), optionsMap["rows"])
        assertEquals(LAST_TRANSACTION_ID, optionsMap["last_transaction_id"])
        assertEquals(START_DATE.toString(), optionsMap["start_date"])
        assertEquals(END_DATE.toString(), optionsMap["end_date"])
        assertEquals(MCC_CODE, optionsMap["mcc"])
        assertEquals(TYPE_1, optionsMap["type"])
    }
}
