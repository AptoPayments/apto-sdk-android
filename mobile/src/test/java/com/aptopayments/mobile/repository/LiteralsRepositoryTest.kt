package com.aptopayments.mobile.repository

import com.aptopayments.mobile.UnitTest
import org.junit.Test
import kotlin.test.assertEquals

private const val MY_VALUE = "my value"
private const val STRING_KEY = "string_key"
private const val STRING_DOT_KEY = "string.key"

class LiteralsRepositoryTest : UnitTest() {

    @Test
    fun `should return server value if it's defined`() {

        // Given
        LiteralsRepository.appendServerLiterals(serverLiterals = hashMapOf(STRING_KEY to MY_VALUE))

        // When
        val value = LiteralsRepository.localized(STRING_KEY)

        // Then
        assertEquals(MY_VALUE, value)
    }

    @Test
    fun `stores keys replacing dots by underline characters`() {

        // When
        LiteralsRepository.appendServerLiterals(hashMapOf(STRING_DOT_KEY to MY_VALUE))

        // Then
        assertEquals(MY_VALUE, LiteralsRepository.localized(STRING_KEY))
    }

    @Test
    fun `replaces dots by underline characters in the input key when localizing`() {

        // When
        LiteralsRepository.appendServerLiterals(hashMapOf(STRING_KEY to MY_VALUE))

        // Then
        assertEquals(MY_VALUE, LiteralsRepository.localized(STRING_DOT_KEY))
    }
}
