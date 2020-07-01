package com.aptopayments.mobile.data.config

import org.junit.Assert.*
import org.junit.Test

class UIConfigTest {

    @Test
    fun `by default dark theme is set to false`() {
        assertFalse(UIConfig.darkTheme)
    }
}
