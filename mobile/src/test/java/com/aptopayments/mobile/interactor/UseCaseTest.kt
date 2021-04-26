package com.aptopayments.mobile.interactor

import com.aptopayments.mobile.functional.Either.Right
import com.aptopayments.mobile.network.ConnectivityCheckerAlwaysConnected
import com.aptopayments.mobile.network.NetworkHandler
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

private const val TYPE_TEST = "Test"
private const val TYPE_PARAM = "ParamTest"

class UseCaseTest {

    private val useCase = MyUseCase()

    @Test
    fun `running use case should return 'Either' of use case type`() {
        val params = MyParams(TYPE_PARAM)
        val result = runBlocking { useCase.run(params) }

        assertEquals(Right(MyType(TYPE_TEST)), result)
    }

    data class MyType(val name: String)
    data class MyParams(val name: String)

    private inner class MyUseCase : UseCase<MyType, MyParams>(NetworkHandler(ConnectivityCheckerAlwaysConnected())) {
        override fun run(params: MyParams) = Right(MyType(TYPE_TEST))
    }
}
