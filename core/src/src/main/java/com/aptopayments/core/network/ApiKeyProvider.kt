package com.aptopayments.core.network

import androidx.annotation.RestrictTo
import com.aptopayments.core.platform.AptoSdkEnvironment

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object ApiKeyProvider {

    var apiKey = ""
        private set
    var environment: AptoSdkEnvironment = AptoSdkEnvironment.PRD
        private set

    fun set(apiKey: String, environment: AptoSdkEnvironment) {
        this.apiKey = apiKey
        this.environment = environment
    }
}
