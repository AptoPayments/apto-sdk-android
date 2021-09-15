package com.aptopayments.mobile.network

import com.aptopayments.mobile.data.TestDataProvider
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ApiCatalogTest {

    private val retrofitFactory: RetrofitFactory = mock()

    private lateinit var sut: ApiCatalog

    @BeforeEach
    fun configure() {
        sut = ApiCatalog(retrofitFactory, ApiKeyProvider)
    }

    @Test
    fun `when create api then correct url is provided`() {
        val apiKey = TestDataProvider.provideAPiKey()
        val environment = TestDataProvider.provideEnvironment()
        ApiKeyProvider.set(apiKey, environment)

        sut.api()

        verify(retrofitFactory).create(environment.baseUrl)
    }

    @Test
    fun `when create vaultApi then correct url is provided`() {
        val apiKey = TestDataProvider.provideAPiKey()
        val environment = TestDataProvider.provideEnvironment()
        ApiKeyProvider.set(apiKey, environment)

        sut.vaultApi()

        verify(retrofitFactory).create(environment.vaultBaseUrl)
    }
}
