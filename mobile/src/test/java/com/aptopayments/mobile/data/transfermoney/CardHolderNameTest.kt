package com.aptopayments.mobile.data.transfermoney

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CardHolderNameTest {
    private val name = "name"
    private val surname = "surname"

    @Test
    internal fun `given name and surname when toString then data is formatted correctly`() {
        val sut = CardHolderName(name, surname)

        assertEquals("$name $surname", sut.toString())
    }
}
