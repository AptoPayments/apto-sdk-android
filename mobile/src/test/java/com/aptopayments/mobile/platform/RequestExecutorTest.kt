package com.aptopayments.mobile.platform

import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.NetworkHandler
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RequestExecutorTest {
    private val networkHandler = mock<NetworkHandler>()
    private val response = mock<Response<Int>>()
    private val call = mock<Call<Int>>() {
        on { execute() } doReturn response
    }
    private val errorHandler = mock<ErrorHandler>() {
        on { handle(response) } doReturn Failure.ServerError(null)
    }

    private val sut = RequestExecutor(networkHandler, errorHandler)

    @Test
    fun `given no connection then NetworkConnection`() {
        whenever(networkHandler.isConnected).thenReturn(false)

        val result = sut.execute(call, {})

        assertTrue(result.isLeft)
        assertTrue((result as Either.Left<*>).a is Failure.NetworkConnection)
    }

    @Test
    fun `given connection and unSuccessful response then ServerError`() {
        whenever(networkHandler.isConnected).thenReturn(true)
        whenever(response.isSuccessful).thenReturn(false)

        val result = sut.execute(call, { it + 1 }, 15)

        assertTrue(result.isLeft)
        assertTrue((result as Either.Left<*>).a is Failure.ServerError)
    }

    @Test
    fun `given connection, successful response, null body and no default then ServerError`() {
        whenever(networkHandler.isConnected).thenReturn(true)
        whenever(response.isSuccessful).thenReturn(true)
        whenever(response.body()).thenReturn(null)
        val result = sut.execute(call, { it + 1 })

        assertTrue(result.isLeft)
        assertTrue((result as Either.Left<*>).a is Failure.ServerError)
    }

    @Test
    fun `given connection and successful response then Right and transformation is executed`() {
        whenever(networkHandler.isConnected).thenReturn(true)
        whenever(response.isSuccessful).thenReturn(true)
        whenever(response.body()).thenReturn(1)

        val result = sut.execute(call, { it + 1 })

        assertTrue(result.isRight)
        assertEquals(2, (result as Either.Right<Int>).b)
    }

    @Test
    fun `given connection and successful response but null then Right and default value transformed`() {
        whenever(networkHandler.isConnected).thenReturn(true)
        whenever(response.isSuccessful).thenReturn(true)
        whenever(response.body()).thenReturn(null)

        val result = sut.execute(call, { it + 1 }, 15)

        assertTrue(result.isRight)
        assertEquals(16, (result as Either.Right<Int>).b)
    }

    @Test
    fun `given error in the conversion then ServerError`() {
        whenever(networkHandler.isConnected).thenReturn(true)
        whenever(response.isSuccessful).thenReturn(true)
        whenever(response.body()).thenReturn(1)

        val result = sut.execute(call, { throw NullPointerException() })

        assertTrue(result.isLeft)
        assert((result as Either.Left<*>).a is Failure.ServerError)
    }

    @Test
    fun `given ConnectException thrown then NetworkConnection`() {
        whenever(networkHandler.isConnected).thenReturn(true)
        whenever(response.isSuccessful).thenReturn(true)
        whenever(call.execute()).thenThrow(ConnectException())

        val result = sut.execute(call, { })

        assertTrue(result.isLeft)
        assert((result as Either.Left<*>).a is Failure.NetworkConnection)
    }

    @Test
    fun `given SocketTimeoutException thrown then NetworkConnection`() {
        whenever(networkHandler.isConnected).thenReturn(true)
        whenever(response.isSuccessful).thenReturn(true)
        whenever(call.execute()).thenThrow(SocketTimeoutException())

        val result = sut.execute(call, { })

        assertTrue(result.isLeft)
        assert((result as Either.Left<*>).a is Failure.NetworkConnection)
    }
}
