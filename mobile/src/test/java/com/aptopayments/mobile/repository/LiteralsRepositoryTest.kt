package com.aptopayments.mobile.repository

import com.aptopayments.mobile.UnitTest
import org.amshove.kluent.`should equal`
import org.junit.Test

class LiteralsRepositoryTest : UnitTest() {

    @Test
    fun `should return server value if it's defined`() {

        // Given
        LiteralsRepository.appendServerLiterals(serverLiterals = hashMapOf("string_key" to "my value"))

        // When
        val value = LiteralsRepository.localized("string_key")

        // Then
        value `should equal` "my value"
    }

    @Test
    fun `stores keys replacing dots by underline characters`() {

        // When
        LiteralsRepository.appendServerLiterals(serverLiterals = hashMapOf("string.key" to "my value"))

        // Then
        LiteralsRepository.localized("string_key") `should equal` "my value"
    }

    @Test
    fun `replaces dots by underline characters in the input key when localizing`() {

        // When
        LiteralsRepository.appendServerLiterals(serverLiterals = hashMapOf("string_key" to "my value"))

        // Then
        LiteralsRepository.localized("string.key") `should equal` "my value"
    }
}
