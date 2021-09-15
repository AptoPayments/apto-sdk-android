package com.aptopayments.mobile.network

import com.aptopayments.mobile.BuildConfig
import com.aptopayments.mobile.ServiceTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.repository.UserSessionRepository
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.inject
import org.koin.dsl.module
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private const val USER_TOKEN = "1234"

internal class FakeServiceTest : ServiceTest() {

    private val userSessionRepository: UserSessionRepository = mock()
    private val apiCatalog: ApiCatalog by inject()
    private val sut by lazy { apiCatalog.api().create(FakeApi::class.java) }

    @BeforeEach
    override fun setup() {
        super.setup()
        loadKoinModules(module { single<UserSessionRepository>(override = true) { userSessionRepository } })
        enqueueContent("")
    }

    @Test
    fun `when have user token then header is included`() {
        whenever(userSessionRepository.isUserSessionValid()).thenReturn(true)
        whenever(userSessionRepository.userToken).thenReturn(USER_TOKEN)

        sut.fakeCall().execute()

        assertEquals("Bearer $USER_TOKEN", takeRequest()?.headers!![X_AUTHORIZATION])
    }

    @Test
    fun `when have user doesn't have token then header is included`() {
        whenever(userSessionRepository.isUserSessionValid()).thenReturn(false)

        sut.fakeCall().execute()

        assertEquals(null, takeRequest()?.headers!![X_AUTHORIZATION])
    }

    @Test
    fun `all fixed headers are present`() {
        sut.fakeCall().execute()

        val request = takeRequest()!!

        assertEquals("Android", request.headers[X_DEVICE_HEADER])
        assertEquals("private, must-revalidate", request.headers[X_CACHE_CONTROL_HEADER])
        assertEquals("application/json", request.headers[X_CONTENT_TYPE_HEADER])
        assertNotNull(request.headers[X_DEVICE_VERSION_HEADER])
        assertEquals(BuildConfig.LIBRARY_VERSION_NAME, request.headers[X_SDK_VERSION_HEADER])
    }

    @Test
    fun `api key header is present`() {
        sut.fakeCall().execute()

        val request = takeRequest()!!

        assertEquals("Bearer ${TestDataProvider.provideAPiKey()}", request.headers[X_API_KEY])
    }
}
