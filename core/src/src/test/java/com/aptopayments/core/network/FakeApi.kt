package com.aptopayments.core.network

import retrofit2.Call
import retrofit2.http.GET

private const val FAKE_PATH = "fake"

internal interface FakeApi {

    @GET(FAKE_PATH)
    fun fakeCall(): Call<Unit>
}
