package com.aptopayments.mobile

import com.aptopayments.mobile.di.applicationModule
import com.aptopayments.mobile.di.repositoryModule
import com.aptopayments.mobile.di.useCasesModule
import com.aptopayments.mobile.network.ConnectivityChecker
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest

internal abstract class UseCaseTest : AutoCloseKoinTest() {

    private val connectivityChecker: ConnectivityChecker = mock() {
        on { isConnected() } doReturn true
    }

    @Before
    open fun setUp() {
        startKoin {
            modules(
                repositoryModule, applicationModule, useCasesModule,
                module {
                    single(override = true) { connectivityChecker }
                }
            )
        }
        setUpKoin()
    }

    abstract fun setUpKoin()
}
