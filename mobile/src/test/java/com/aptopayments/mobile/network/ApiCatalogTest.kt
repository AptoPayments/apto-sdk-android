package com.aptopayments.mobile.network

import com.aptopayments.mobile.UnitTest
import com.aptopayments.mobile.data.TestDataProvider
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class ApiCatalogTest : UnitTest() {

    @Mock
    private lateinit var retrofitFactory: RetrofitFactory

    private lateinit var sut: ApiCatalog

    @Before
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
