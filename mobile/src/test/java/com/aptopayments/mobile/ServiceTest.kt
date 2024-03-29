package com.aptopayments.mobile

import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.di.applicationModule
import com.aptopayments.mobile.di.repositoryModule
import com.aptopayments.mobile.network.*
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.UserSessionRepositoryDouble
import com.aptopayments.mobile.utils.FileReader
import com.google.gson.JsonParser
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection.HTTP_OK
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

internal abstract class ServiceTest : UnitTest() {

    open var environments = listOf(Environment.NORMAL)

    private val apiKeyProvider: ApiKeyProvider = mock()
    protected val networkHandler: NetworkHandler = mock()
    private var server: MockWebServer = MockWebServer()
    protected val gson = GsonProvider.provide()
    private val jsonParser = JsonParser()

    @BeforeEach
    open fun setup() {
        server.start()
        configureEnvironment()
        configureKoin()
    }

    @AfterEach
    override fun afterEach() {
        server.shutdown()
        super.afterEach()
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
        val requestJson = jsonParser.parse(takeRequest()?.body?.readUtf8())
        val fileJson = jsonParser.parse(readFile(fileName))

        assertEquals(requestJson, fileJson)
    }

    protected fun <T> assertCode(test: Response<T>, code: Int = HTTP_OK) {
        assertEquals(code, test.code())
    }

    protected fun <T> parseEntity(fileContent: String, className: Class<T>): T = gson.fromJson(fileContent, className)

    private fun configureKoin() {
        startKoin { modules(applicationModule, repositoryModule) }
        loadKoinModules(
            module {
                single<UserSessionRepository>(override = true) { UserSessionRepositoryDouble() }
                factory<OkHttpClientProvider>(override = true) { SimpleOkHttpClientProvider(apiKeyProvider, get()) }
                single(override = true) { ApiCatalog(get(), apiKeyProvider) }
                single(override = true) { networkHandler }
            }
        )
    }

    protected open fun configureEnvironment() {
        whenever(apiKeyProvider.apiKey).thenReturn(TestDataProvider.provideAPiKey())
        mockEnvironments()
    }

    private fun mockEnvironments() {
        environments.forEach {
            when (it) {
                Environment.NORMAL -> mockNormalEnvironment()
                Environment.VAULT -> mockVaultEnvironment()
            }
        }
    }

    private fun mockVaultEnvironment() {
        whenever(apiKeyProvider.getEnvironmentVaultUrl()).thenReturn(getServerUrl())
    }

    private fun mockNormalEnvironment() {
        whenever(apiKeyProvider.getEnvironmentUrl()).thenReturn(getServerUrl())
    }

    private fun getServerUrl() = server.url("").toString()

    protected fun takeRequest() = server.takeRequest(1, TimeUnit.SECONDS)

    @Throws(IOException::class)
    protected fun readFile(jsonFileName: String): String {
        return FileReader().readFile(jsonFileName, "api-calls")
    }

    enum class Environment {
        NORMAL, VAULT
    }
}
