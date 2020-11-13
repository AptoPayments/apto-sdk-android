package com.aptopayments.mobile.network

import androidx.annotation.RestrictTo
import com.aptopayments.mobile.platform.AptoSdkEnvironment

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object ApiKeyProvider {

    var apiKey = ""
        private set
    var environment = AptoSdkEnvironment.PRD
        private set

    fun getEnvironmentUrl() = environment.baseUrl
    fun getEnvironmentVaultUrl() = environment.vaultBaseUrl

    fun set(apiKey: String, environment: AptoSdkEnvironment) {
        this.apiKey = apiKey
        this.environment = environment
    }

    fun isCurrentEnvironmentPrd() = environment == AptoSdkEnvironment.PRD
}
