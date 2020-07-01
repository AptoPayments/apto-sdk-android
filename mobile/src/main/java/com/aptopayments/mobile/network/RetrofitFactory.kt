package com.aptopayments.mobile.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal interface RetrofitFactory {
    fun create(url: String): Retrofit
}

internal class RetrofitFactoryImpl(
    private val gsonProvider: GsonProvider,
    private val okHttpClientProvider: OkHttpClientProvider
) : RetrofitFactory {

    override fun create(url: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .client(createOkHttpClient())
            .addConverterFactory(getConverterFactory())
            .build()

    private fun getConverterFactory() = GsonConverterFactory.create(gsonProvider.provide())

    private fun createOkHttpClient() = okHttpClientProvider.provide()
}
