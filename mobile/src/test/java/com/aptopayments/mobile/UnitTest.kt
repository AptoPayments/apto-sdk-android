package com.aptopayments.mobile

import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.mockito.junit.MockitoJUnitRunner

/**
 * Base class for Unit tests. Inherit from it to create test cases which DO NOT contain android
 * framework dependencies or components.
 *
 * @see AndroidTest
 */
@RunWith(MockitoJUnitRunner::class)
abstract class UnitTest : AutoCloseKoinTest()
