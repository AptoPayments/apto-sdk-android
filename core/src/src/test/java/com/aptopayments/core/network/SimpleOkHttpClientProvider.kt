package com.aptopayments.core.network

import com.aptopayments.core.repository.UserSessionRepository
import okhttp3.OkHttpClient

class SimpleOkHttpClientProvider(
    apiKeyProvider: ApiKeyProvider,
    userSessionRepository: UserSessionRepository
) : OkHttpClientProviderImpl(apiKeyProvider, userSessionRepository) {

    override fun configureConnection(okHttpClientBuilder: OkHttpClient.Builder) {
        // Do nothing
    }
}
