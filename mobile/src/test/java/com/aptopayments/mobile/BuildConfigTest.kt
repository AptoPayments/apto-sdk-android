package com.aptopayments.mobile

import org.junit.jupiter.api.Test
import kotlin.test.assertNotEquals

class BuildConfigTest {

    @Test
    internal fun `given LIBRARY_VERSION is not null`() {
        assertNotEquals("null", BuildConfig.LIBRARY_VERSION_NAME)
    }
}
