package com.aptopayments.core.platform

enum class AptoSdkEnvironment(val baseUrl: String, val vaultBaseUrl: String) {
    LOCAL_EMULATOR("http://10.0.2.2:5001", "http://10.0.2.2:5001"),
    LOCAL_DEVICE("http://local.ledge.me:5001", "http://local.ledge.me:5001"),
    STG("https://api.stg.aptopayments.com", "https://vault.stg.aptopayments.com"),
    SBX("https://api.sbx.aptopayments.com", "https://vault.sbx.aptopayments.com"),
    PRD("https://api.aptopayments.com", "https://vault.aptopayments.com")
}
