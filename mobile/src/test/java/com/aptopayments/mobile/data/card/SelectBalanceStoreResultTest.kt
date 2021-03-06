package com.aptopayments.mobile.data.card

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SelectBalanceStoreResultTest {
    @Test
    fun `error message return expected error message`() {
        // Given
        val sut = SelectBalanceStoreResult(SelectBalanceStoreResult.Type.INVALID, 90191)

        // When
        val errorMessage = sut.errorMessage()

        // Then
        assertEquals("select_balance_store.login.error_wrong_country.message", errorMessage)
    }

    @Test
    fun `sut initialized with custom error keys error message return custom error`() {
        // Given
        val expectedError = "external_oauth.login.error_wrong_country.message"
        val sut = SelectBalanceStoreResult(SelectBalanceStoreResult.Type.INVALID, 90191, listOf(expectedError))

        // When
        val errorMessage = sut.errorMessage()

        // Then
        assertEquals(expectedError, errorMessage)
    }
}
