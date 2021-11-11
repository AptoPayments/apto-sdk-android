package com.aptopayments.mobile.platform

import java.util.Locale

enum class AptoSdkEnvironment(val baseUrl: String, val vaultBaseUrl: String) {
    STG("https://api.stg.aptopayments.com", "https://vault.stg.aptopayments.com"),
    SBX("https://api.sbx.aptopayments.com", "https://vault.sbx.aptopayments.com"),
    PRD("https://api.aptopayments.com", "https://vault.aptopayments.com");

    companion object {
        fun fromString(str: String) = valueOf(str.uppercase(Locale.US))
    }
}
