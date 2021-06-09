package com.aptopayments.mobile.platform

import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.exception.server.ServerErrorFactory
import com.aptopayments.mobile.repository.UserSessionRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import okhttp3.ResponseBody
import org.junit.jupiter.api.Test
import retrofit2.Response
import kotlin.test.assertEquals

private const val ERROR_MESSAGE = "I am an error"

class ErrorHandlerTest {

    private val userSessionRepository = mock<UserSessionRepository>()
    private val response = mock<Response<Any>>()
    private val sut = ErrorHandler(userSessionRepository, ServerErrorFactory())

    @Test
    fun `when 401 then UserSessionExpired`() {
        whenever(response.code()).thenReturn(401)

        val result = sut.handle(response)

        verify(userSessionRepository).clearUserSession()
        assertEquals(Failure.UserSessionExpired, result)
    }

    @Test
    fun `when 401 then DeprecatedSdk`() {
        whenever(response.code()).thenReturn(412)

        val result = sut.handle(response)

        assertEquals(Failure.DeprecatedSDK, result)
    }

    @Test
    fun `when 429 then RateLimitFailure`() {
        whenever(response.code()).thenReturn(429)

        val result = sut.handle(response)

        assertEquals(Failure.RateLimitFailure, result)
    }

    @Test
    fun `when 503 then MaintenanceMode`() {
        whenever(response.code()).thenReturn(503)

        val result = sut.handle(response)

        assertEquals(Failure.MaintenanceMode, result)
    }

    @Test
    fun `when 400 with no error body then Generic server error`() {
        whenever(response.code()).thenReturn(400)
        whenever(response.errorBody()).thenReturn(null)

        val result = sut.handle(response)

        assert(result is Failure.ServerError)
    }

    @Test
    fun `when 400 with error body then Server error code matches`() {
        whenever(response.code()).thenReturn(400)
        val errorBody = mock<ResponseBody>() {
            on { string() } doReturn "{'code' : '300'}"
        }
        whenever(response.errorBody()).thenReturn(errorBody)

        val result = sut.handle(response)

        assert(result is Failure.ServerError)
        assertEquals(300, (result as Failure.ServerError).code)
    }

    @Test
    fun `when 400 with error body then Server message matches`() {
        whenever(response.code()).thenReturn(400)
        val errorBody = mock<ResponseBody>() {
            on { string() } doReturn "{'message' : '$ERROR_MESSAGE'}"
        }
        whenever(response.errorBody()).thenReturn(errorBody)

        val result = sut.handle(response)

        assert(result is Failure.ServerError)
        assertEquals(ERROR_MESSAGE, (result as Failure.ServerError).apiMessage)
    }
}
