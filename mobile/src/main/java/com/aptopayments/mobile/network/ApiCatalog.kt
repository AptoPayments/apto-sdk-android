package com.aptopayments.mobile.network

internal class ApiCatalog(
    private val retrofitFactory: RetrofitFactory,
    private val apiKeyProvider: ApiKeyProvider
) {

    fun api() = retrofitFactory.create(apiKeyProvider.getEnvironmentUrl())

    fun vaultApi() = retrofitFactory.create(apiKeyProvider.getEnvironmentVaultUrl())
}
