package com.aptopayments.core.network

import com.aptopayments.core.repository.UserSessionRepository
import okhttp3.OkHttpClient

internal class SimpleOkHttpClientProvider(
    apiKeyProvider: ApiKeyProvider,
    userSessionRepository: UserSessionRepository
) : OkHttpClientProviderImpl(apiKeyProvider, userSessionRepository) {

    override fun configureConnection(okHttpClientBuilder: OkHttpClient.Builder) {
        // Do nothing
    }
}
