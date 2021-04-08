package com.aptopayments.mobile.data.config

import org.junit.Assert.*
import org.junit.Test

class UIStatusBarStyleTest {
    @Test
    fun `given unknown type when parseStatusBarStyle then UNKNOWN is returned`() {
        val result = UIStatusBarStyle.parseStatusBarStyle("test")

        assertEquals(UIStatusBarStyle.AUTO, result)
    }

    @Test
    fun `given dark type when parseStatusBarStyle then DARK is returned`() {
        val result = UIStatusBarStyle.parseStatusBarStyle("dark")

        assertEquals(UIStatusBarStyle.DARK, result)
    }
}
