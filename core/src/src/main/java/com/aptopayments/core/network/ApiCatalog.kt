package com.aptopayments.core.network

internal class ApiCatalog(
    private val retrofitFactory: RetrofitFactory
) {

    fun api() = retrofitFactory.create(ApiKeyProvider.environment.baseUrl)

    fun vaultApi() = retrofitFactory.create(ApiKeyProvider.environment.vaultBaseUrl)

}
