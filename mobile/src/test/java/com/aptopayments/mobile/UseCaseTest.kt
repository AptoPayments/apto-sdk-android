package com.aptopayments.mobile

import com.aptopayments.mobile.di.applicationModule
import com.aptopayments.mobile.di.repositoryModule
import com.aptopayments.mobile.di.useCasesModule
import com.aptopayments.mobile.network.ConnectivityChecker
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.startKoin
import org.koin.dsl.module

internal abstract class UseCaseTest : UnitTest() {

    private val connectivityChecker: ConnectivityChecker = mock() {
        on { isConnected() } doReturn true
    }

    @BeforeEach
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
