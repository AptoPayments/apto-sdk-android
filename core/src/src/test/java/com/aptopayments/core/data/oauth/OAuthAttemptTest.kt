package com.aptopayments.core.data.oauth

import android.content.Context
import com.aptopayments.core.UnitTest
import com.aptopayments.core.data.oauth.OAuthAttemptStatus.FAILED
import com.aptopayments.core.data.oauth.OAuthAttemptStatus.PASSED
import com.aptopayments.core.data.oauth.OAuthAttemptStatus.PENDING
import org.amshove.kluent.mock
import org.junit.Test
import kotlin.test.assertEquals

class OAuthAttemptTest : UnitTest() {
    @Test
    fun `pending attempt error message is empty`() {
        // Given
        val sut = OAuthAttempt(id = "id", status = PENDING, url = null, tokenId = "", userData = null,
                error = "invalid_request", errorMessage = "Invalid request")

        // When
        val errorMessage = sut.localizedErrorMessage(mock(Context::class))

        // Then
        assertEquals("", errorMessage)
    }

    @Test
    fun `passed attempt error message is empty`() {
        // Given
        val sut = OAuthAttempt(id = "id", status = PASSED, url = null, tokenId = "", userData = null,
                error = "invalid_request", errorMessage = "Invalid request")

        // When
        val errorMessage = sut.localizedErrorMessage(mock(Context::class))

        // Then
        assertEquals("", errorMessage)
    }

    @Test
    fun `localized error message return expected error message`() {
        // Given
        val sut = OAuthAttempt(id = "id", status = FAILED, url = null, tokenId = "", userData = null,
                error = "invalid_request", errorMessage = "Invalid request")

        // When
        val errorMessage = sut.localizedErrorMessage(mock(Context::class))

        // Then
        assertEquals("select_balance_store.login.error_oauth_invalid_request.message", errorMessage)
    }

    @Test
    fun `sut initialized with custom error keys localized error message return custom error`() {
        // Given
        val expectedError = "external_auth.login.error_oauth_invalid_request.message"
        val sut = OAuthAttempt(id = "id", status = FAILED, url = null, tokenId = "", userData = null,
                error = "invalid_request", errorMessage = "Invalid request", errorMessageKeys = listOf(expectedError))

        // When
        val errorMessage = sut.localizedErrorMessage(mock(Context::class))

        // Then
        assertEquals(expectedError, errorMessage)
    }
}
