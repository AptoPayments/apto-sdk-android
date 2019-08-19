package com.aptopayments.core.repository

import android.content.Context
import com.aptopayments.core.UnitTest
import org.amshove.kluent.`should equal`
import org.junit.Test
import org.mockito.Mock

class LiteralsRepositoryTest: UnitTest() {

    @Mock
    lateinit var mockContext: Context

    @Test
    fun `should return server value if it's defined`() {

        // Given
        LiteralsRepository.appendServerLiterals(serverLiterals = hashMapOf("string_key" to "my value"))

        // When
        val value = LiteralsRepository.localized(mockContext, "string_key")

        // Then
        value `should equal` "my value"
    }

    @Test
    fun `stores keys replacing dots by underline characters`() {

        // When
        LiteralsRepository.appendServerLiterals(serverLiterals = hashMapOf("string.key" to "my value"))

        // Then
        LiteralsRepository.localized(mockContext, "string_key") `should equal` "my value"
    }

    @Test
    fun `replaces dots by underline characters in the input key when localizing`() {

        // When
        LiteralsRepository.appendServerLiterals(serverLiterals = hashMapOf("string_key" to "my value"))

        // Then
        LiteralsRepository.localized(mockContext, "string.key") `should equal` "my value"
    }
}
