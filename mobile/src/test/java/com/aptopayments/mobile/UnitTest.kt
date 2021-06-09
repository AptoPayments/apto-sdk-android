package com.aptopayments.mobile

import org.junit.jupiter.api.AfterEach
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

/**
 * Base class for Unit tests. Inherit from it to create test cases which DO NOT contain android
 * framework dependencies or components.
 *
 * @see AndroidTest
 */
abstract class UnitTest : KoinTest {

    @AfterEach
    open fun afterEach() {
        stopKoin()
    }
}
