package com.aptopayments.mobile.repository.config.remote

import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.platform.BaseService

internal class ConfigService constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val configApi by lazy { apiCatalog.api().create(ConfigApi::class.java) }

    fun getContextConfiguration() = configApi.getContextConfiguration()

    fun getCardProduct(cardProductId: String) = configApi.getCardProduct(cardProductId = cardProductId)

    fun getCardProducts() = configApi.getCardProducts()
}
