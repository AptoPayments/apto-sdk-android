package com.aptopayments.core.platform

interface AptoPlatformDelegate {

    /**
     * This method is called when a network request fails because the current SDK version has been deprecated. To know
     * the version of the SDK use `ShiftSDK.version`.
     */
    fun sdkDeprecated()
}
