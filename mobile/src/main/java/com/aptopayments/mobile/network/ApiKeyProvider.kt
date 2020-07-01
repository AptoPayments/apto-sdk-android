package com.aptopayments.mobile.network

import androidx.annotation.RestrictTo
import com.aptopayments.mobile.platform.AptoSdkEnvironment

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object ApiKeyProvider {

    var apiKey = ""
        private set
    private var environment = AptoSdkEnvironment.PRD

    fun getEnvironmentUrl() = environment.baseUrl
    fun getEnvironmentVaultUrl() = environment.vaultBaseUrl

    fun set(apiKey: String, environment: AptoSdkEnvironment) {
        this.apiKey = apiKey
        this.environment = environment
    }
}
