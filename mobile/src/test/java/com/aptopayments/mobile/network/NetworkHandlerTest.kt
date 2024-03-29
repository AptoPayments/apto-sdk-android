package com.aptopayments.mobile.network

import org.mockito.kotlin.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

private const val INSTANCE = "test"

class NetworkHandlerTest {

    private val connectivityChecker: ConnectivityChecker = mock()

    private lateinit var sut: NetworkHandler

    @BeforeEach
    fun setUp() {
        sut = NetworkHandler(connectivityChecker)
    }

    @Test
    fun `when no internet then isConnected return false`() {
        whenever(connectivityChecker.isConnected()).thenReturn(false)

        assertFalse(sut.isConnected)
    }

    @Test
    fun `when there is internet then isConnected return true`() {
        whenever(connectivityChecker.isConnected()).thenReturn(true)

        assertTrue(sut.isConnected)
    }

    @Test
    fun `when subscribed to networkreachability and event fired then lambda is called`() {
        val callbackMock: (Boolean) -> Unit = mock()
        sut.subscribeNetworkReachabilityListener(INSTANCE, callbackMock)

        sut.networkNotReachable()

        verify(callbackMock).invoke(false)
    }

    @Test
    fun `when subscribed and unsubscribed to networkreachability and event fired then lambda is not called`() {
        val callbackMock: (Boolean) -> Unit = mock()
        sut.subscribeNetworkReachabilityListener(INSTANCE, callbackMock)
        sut.unsubscribeNetworkReachabilityListener(INSTANCE)

        sut.networkNotReachable()

        verify(callbackMock, times(0)).invoke(any())
    }

    @Test
    fun `when subscribed to maintenance and event fired then lambda is called with false`() {
        val callbackMock: (Boolean) -> Unit = mock()
        sut.subscribeMaintenanceListener(INSTANCE, callbackMock)

        sut.maintenanceModeDetected()

        verify(callbackMock).invoke(false)
    }

    @Test
    fun `when subscribed to maintenance and check fired then lambda is called with true`() {
        val callbackMock: (Boolean) -> Unit = mock()
        sut.subscribeMaintenanceListener(INSTANCE, callbackMock)

        sut.checkMaintenanceMode()

        verify(callbackMock).invoke(true)
    }

    @Test
    fun `when subscribed and unsubscribed to maintenance and event fired then lambda is not called`() {
        val callbackMock: (Boolean) -> Unit = mock()

        sut.subscribeMaintenanceListener(INSTANCE, callbackMock)
        sut.unsubscribeMaintenanceListener(INSTANCE)
        sut.networkNotReachable()
        verify(callbackMock, times(0)).invoke(any())
    }

    @Test
    fun `when subscribed to deprecatedListener and event fired then lambda is called`() {
        val callbackMock: (Boolean) -> Unit = mock()
        sut.subscribeDeprecatedSdkListener(INSTANCE, callbackMock)

        sut.deprecatedSdkDetected()

        verify(callbackMock).invoke(true)
    }
}
