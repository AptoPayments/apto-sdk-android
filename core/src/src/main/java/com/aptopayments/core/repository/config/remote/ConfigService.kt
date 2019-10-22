package com.aptopayments.core.repository.config.remote

import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.platform.BaseService

internal class ConfigService constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val configApi by lazy { apiCatalog.api().create(ConfigApi::class.java) }

    fun getContextConfiguration() = configApi.getContextConfiguration()

    fun getCardProduct(cardProductId: String) = configApi.getCardProduct(
        userToken = authorizationHeader(userSessionRepository.userToken),
        cardProductId = cardProductId
    )

    fun getCardProducts() = configApi.getCardProducts(
        userToken = authorizationHeader(userSessionRepository.userToken)
    )
}
