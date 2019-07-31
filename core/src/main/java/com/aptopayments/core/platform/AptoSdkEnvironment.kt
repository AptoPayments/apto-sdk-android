package com.aptopayments.core.platform

enum class AptoSdkEnvironment(val baseUrl: String, val vaultBaseUrl: String) {
    LOCAL_EMULATOR("http://10.0.2.2:5001", "http://10.0.2.2:5001"),
    LOCAL_DEVICE("http://local.ledge.me:5001", "http://local.ledge.me:5001"),
    DEV("https://api.ux.dev2.8583.io", "https://vault.ux.dev2.8583.io"),
    STG("https://stg.ledge.me", "https://vault.stg.ledge.me"),
    SBX("https://api.ux.sbx.aptopayments.com", "https://vault.ux.sbx.aptopayments.com"),
    PRD("https://api.ux.8583.io", "https://vault.ux.8583.io")
}
