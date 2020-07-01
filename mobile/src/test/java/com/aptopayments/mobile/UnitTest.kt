package com.aptopayments.mobile

import android.app.Application
import com.aptopayments.mobile.platform.AptoPlatform
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
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
abstract class UnitTest : AutoCloseKoinTest() {

    @Suppress("LeakingThis")
    @Rule
    @JvmField
    val injectMocks = InjectMocksRule.create(this@UnitTest)

    @Before
    open fun setUp() {
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUpAplication() {
            AptoPlatform.application = Application()
        }
    }
}
