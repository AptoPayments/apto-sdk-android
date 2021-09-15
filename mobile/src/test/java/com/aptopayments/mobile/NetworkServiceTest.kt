package com.aptopayments.mobile

import org.mockito.kotlin.whenever

internal abstract class NetworkServiceTest : ServiceTest() {

    override fun configureEnvironment() {
        super.configureEnvironment()
        whenever(networkHandler.isConnected).thenReturn(true)
    }
}
