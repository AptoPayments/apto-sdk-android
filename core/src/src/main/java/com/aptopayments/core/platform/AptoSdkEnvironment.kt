package com.aptopayments.core.platform

enum class AptoSdkEnvironment(val baseUrl: String, val vaultBaseUrl: String) {
    LOCAL_EMULATOR("http://10.0.2.2:5001", "http://10.0.2.2:5001"),
    LOCAL_DEVICE("http://local.ledge.me:5001", "http://local.ledge.me:5001"),
    DEV("https://api.ux.dev2.aptopayments.com", "https://vault.ux.dev2.aptopayments.com"),
    STG("https://api.ux.stg.aptopayments.com", "https://vault.ux.stg.aptopayments.com"),
    SBX("https://api.ux.sbx.aptopayments.com", "https://vault.ux.sbx.aptopayments.com"),
    PRD("https://api.ux.aptopayments.com", "https://vault.ux.aptopayments.com")
}
