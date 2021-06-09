package com.aptopayments.mobile.data.config

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UIConfigTest {

    @Test
    fun `by default dark theme is set to false`() {
        assertFalse(UIConfig.darkTheme)
    }
}
