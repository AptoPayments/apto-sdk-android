package com.aptopayments.mobile.network

import com.aptopayments.mobile.repository.UserSessionRepository
import okhttp3.OkHttpClient

internal class SimpleOkHttpClientProvider(
    apiKeyProvider: ApiKeyProvider,
    userSessionRepository: UserSessionRepository
) : OkHttpClientProviderImpl(apiKeyProvider, userSessionRepository) {

    override fun configureConnection(okHttpClientBuilder: OkHttpClient.Builder) {
        // Do nothing
    }
}
