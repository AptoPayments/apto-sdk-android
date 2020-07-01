package com.aptopayments.mobile

import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.di.applicationModule
import com.aptopayments.mobile.di.repositoryModule
import com.aptopayments.mobile.network.*
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.UserSessionRepositoryDouble
import com.nhaarman.mockitokotlin2.whenever
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.mockito.Mock
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection.HTTP_OK
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal abstract class ServiceTest : UnitTest() {

    @Mock
    lateinit var apiKeyProvider: ApiKeyProvider

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
        })
    }

    private fun configureEnvironment() {
        whenever(apiKeyProvider.apiKey).thenReturn(TestDataProvider.provideAPiKey())
        whenever(apiKeyProvider.getEnvironmentUrl()).thenReturn(getServerUrl())
    }

    private fun getServerUrl() = server.url("").toString()

    protected fun takeRequest() = server.takeRequest(1, TimeUnit.SECONDS)

    @Throws(IOException::class)
    protected fun readFile(jsonFileName: String): String {
        val inputStream = this::class.java.getResourceAsStream("/api-calls/$jsonFileName")
            ?: throw NullPointerException("Have you added the local resource correctly?, Hint: name it as: $jsonFileName")
        val stringBuilder = StringBuilder()
        var inputStreamReader: InputStreamReader? = null
        try {
            inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var character: Int = bufferedReader.read()
            while (character != -1) {
                stringBuilder.append(character.toChar())
                character = bufferedReader.read()
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
        } finally {
            inputStream.close()
            inputStreamReader?.close()
        }
        return stringBuilder.toString()
    }
}
