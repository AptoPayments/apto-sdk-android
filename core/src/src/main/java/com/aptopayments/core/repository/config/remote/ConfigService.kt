package com.aptopayments.core.repository.config.remote

import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.platform.BaseService
import javax.inject.Inject

internal class ConfigService
@Inject
constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val configApi by lazy { apiCatalog.api().create(ConfigApi::class.java) }

    fun getContextConfiguration() = configApi.getContextConfiguration(ApiCatalog.apiKey)
    fun getCardProduct(cardProductId: String) = configApi.getCardProduct(
            apiKey = ApiCatalog.apiKey,
            userToken = authorizationHeader(userSessionRepository.userToken),
            cardProductId = cardProductId)
    fun getCardProducts() = configApi.getCardProducts(
            apiKey = ApiCatalog.apiKey,
            userToken = authorizationHeader(userSessionRepository.userToken))
}
