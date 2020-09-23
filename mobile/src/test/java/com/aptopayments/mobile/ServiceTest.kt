package com.aptopayments.mobile

import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.di.applicationModule
import com.aptopayments.mobile.di.repositoryModule
import com.aptopayments.mobile.network.*
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.UserSessionRepositoryDouble
import com.aptopayments.mobile.utils.FileReader
import com.nhaarman.mockitokotlin2.whenever
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.mockito.Mock
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection.HTTP_OK
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal abstract class ServiceTest : UnitTest() {

    @Mock
    lateinit var apiKeyProvider: ApiKeyProvider

    @Mock
    lateinit var networkHandler: NetworkHandler

    private var server: MockWebServer = MockWebServer()
    protected val gson = GsonProvider.provide()

    @Before
    open fun setup() {
        server.start()
        configureEnvironment()
        configureKoin()
    }

    @After
    fun tearDown() {
        server.shutdown()
        stopKoin()
    }

    protected fun enqueueContent(content: String, code: Int = HTTP_OK) {
        server.enqueue(MockResponse().setResponseCode(code).setBody(content))
    }

    protected fun enqueueFile(fileName: String, code: Int = HTTP_OK) {
        val fileContent = readFile(fileName)
        enqueueContent(fileContent, code)
    }

    protected fun assertRequestSentTo(url: String, method: String = "GET") {
        val request = takeRequest()
        assertEquals("/$url", request?.path)
        assertEquals(method, request?.method)
    }

    protected fun assertRequestBodyFile(fileName: String) {
        val request = takeRequest()
        val fileContent = readFile(fileName)
        assertTrue { request?.body.toString().contains(fileContent.trim()) }
    }

    protected fun <T> assertCode(test: Response<T>, code: Int = HTTP_OK) {
        assertEquals(code, test.code())
    }

    protected fun <T> parseEntity(fileContent: String, className: Class<T>): T = gson.fromJson(fileContent, className)

    private fun configureKoin() {
        startKoin { modules(applicationModule, repositoryModule) }
        loadKoinModules(module {
            single<UserSessionRepository>(override = true) { UserSessionRepositoryDouble() }
            factory<OkHttpClientProvider>(override = true) { SimpleOkHttpClientProvider(apiKeyProvider, get()) }
            single(override = true) { ApiCatalog(get(), apiKeyProvider) }
            single(override = true) { networkHandler }
        })
    }

    protected open fun configureEnvironment() {
        whenever(apiKeyProvider.apiKey).thenReturn(TestDataProvider.provideAPiKey())
        whenever(apiKeyProvider.getEnvironmentUrl()).thenReturn(getServerUrl())
    }

    private fun getServerUrl() = server.url("").toString()

    protected fun takeRequest() = server.takeRequest(1, TimeUnit.SECONDS)

    @Throws(IOException::class)
    protected fun readFile(jsonFileName: String): String {
        return FileReader().readFile(jsonFileName, "api-calls")
    }
}
