package com.aptopayments.mobile.repository.config.remote

import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.platform.BaseNetworkService
import com.aptopayments.mobile.repository.config.remote.entities.CardConfigurationEntity
import com.aptopayments.mobile.repository.config.remote.entities.ContextConfigurationEntity

internal class ConfigService constructor(apiCatalog: ApiCatalog) : BaseNetworkService() {

    private val configApi by lazy { apiCatalog.api().create(ConfigApi::class.java) }

    fun getContextConfiguration() =
        request(
            configApi.getContextConfiguration(),
            { it.toContextConfiguration() },
            ContextConfigurationEntity()
        )

    fun getCardProduct(cardProductId: String) =
        request(
            configApi.getCardProduct(cardProductId = cardProductId),
            { it.toCardProduct() },
            CardConfigurationEntity()
        )

    fun getCardProducts() =
        request(configApi.getCardProducts(), { listEntity ->
            listEntity.data?.map {
                it.toCardProductSummary()
            } ?: emptyList()
        }, ListEntity())
}
