package com.aptopayments.mobile

import com.nhaarman.mockitokotlin2.whenever

internal abstract class NetworkServiceTest : ServiceTest() {

    override fun configureEnvironment() {
        super.configureEnvironment()
        whenever(networkHandler.isConnected).thenReturn(true)
    }
}
