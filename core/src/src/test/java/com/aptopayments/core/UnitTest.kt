package com.aptopayments.core

import com.aptopayments.core.di.CoreApplicationComponent
import com.aptopayments.core.platform.AptoPlatform
import com.aptopayments.core.platform.UseCasesWrapper
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Base class for Unit tests. Inherit from it to create test cases which DO NOT contain android
 * framework dependencies or components.
 *
 * @see AndroidTest
 */
@RunWith(MockitoJUnitRunner::class)
abstract class UnitTest {

    @Suppress("LeakingThis")
    @Rule @JvmField val injectMocks = InjectMocksRule.create(this@UnitTest)

    @Before
    open fun setUp() {
        val mockApplicationComponent = mock<CoreApplicationComponent>()
        AptoPlatform.appComponent = mockApplicationComponent
        AptoPlatform.useCasesWrapper = UseCasesWrapper(AptoPlatform.appComponent)
    }
}
