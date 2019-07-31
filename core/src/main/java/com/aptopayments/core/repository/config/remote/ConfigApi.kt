package com.aptopayments.core.repository.config.remote

import com.aptopayments.core.network.ListEntity
import com.aptopayments.core.network.X_API_KEY
import com.aptopayments.core.network.X_AUTHORIZATION
import com.aptopayments.core.repository.config.remote.entities.CardConfigurationEntity
import com.aptopayments.core.repository.config.remote.entities.CardProductSummaryEntity
import com.aptopayments.core.repository.config.remote.entities.ContextConfigurationEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

private const val PROJECT_CONFIG_PATH = "v1/config"
private const val CARD_CONFIG_PATH = "v1/config/cardproducts/{id}"
private const val CARD_PRODUCTS_PATH = "v1/config/cardproducts"
private const val CARD_PRODUCT_ID = "id"

internal interface ConfigApi {

    @GET(PROJECT_CONFIG_PATH)
    fun getContextConfiguration(
            @Header(X_API_KEY) apiKey: String
    ): Call<ContextConfigurationEntity>

    @GET(CARD_CONFIG_PATH)
    fun getCardProduct(
            @Header(X_API_KEY) apiKey: String,
            @Header(X_AUTHORIZATION) userToken: String,
            @Path(CARD_PRODUCT_ID) cardProductId: String
    ): Call<CardConfigurationEntity>

    @GET(CARD_PRODUCTS_PATH)
    fun getCardProducts(
            @Header(X_API_KEY) apiKey: String,
            @Header(X_AUTHORIZATION) userToken: String
    ): Call<ListEntity<CardProductSummaryEntity>>
}
