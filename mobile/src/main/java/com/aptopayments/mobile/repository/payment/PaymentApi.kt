package com.aptopayments.mobile.repository.payment

import com.aptopayments.mobile.repository.payment.remote.entities.PaymentEntityWrapper
import com.aptopayments.mobile.repository.payment.remote.requests.PushFundsRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

private const val PAYMENT = "payment_sources/{payment_source_id}/push"
private const val PAYMENT_SOURCE_ID = "payment_source_id"

internal interface PaymentApi {

    @POST(PAYMENT)
    fun pushFunds(
        @Path(PAYMENT_SOURCE_ID) paymentSourceId: String,
        @Body request: PushFundsRequest
    ): Call<PaymentEntityWrapper>
}
