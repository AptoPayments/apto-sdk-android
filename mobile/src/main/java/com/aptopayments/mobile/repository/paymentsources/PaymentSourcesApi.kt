package com.aptopayments.mobile.repository.paymentsources

import com.aptopayments.mobile.repository.paymentsources.remote.entities.PaymentSourceEntity
import com.aptopayments.mobile.repository.paymentsources.remote.entities.PaymentSourceWrapperEntity
import com.aptopayments.mobile.repository.paymentsources.remote.entities.PaymentSourcesListEntity
import com.aptopayments.mobile.repository.paymentsources.remote.requests.AddPaymentSourceRequest
import com.aptopayments.mobile.repository.paymentsources.remote.requests.UpdatePaymentSourceRequest
import retrofit2.Call
import retrofit2.http.*

private const val ADD_PAYMENT_SOURCE = "v1/payment_sources"
private const val GET_PAYMENT_SOURCE_LIST = "v1/payment_sources"
private const val PAYMENT_SOURCE_ID = "payment_source_id"
private const val UPDATE_PAYMENT_SOURCE = "v1/payment_sources/{payment_source_id}"
private const val DELETE_PAYMENT_SOURCE = "v1/payment_sources/{payment_source_id}"

private const val PARAMETER_STARTING_AFTER = "starting_after"
private const val PARAMETER_ENDING_BEFORE = "ending_before"
private const val PARAMETER_LIMIT = "limit"

internal interface PaymentSourcesApi {

    @POST(ADD_PAYMENT_SOURCE)
    fun addPaymentSource(@Body request: AddPaymentSourceRequest): Call<PaymentSourceWrapperEntity>

    @GET(GET_PAYMENT_SOURCE_LIST)
    fun getPaymentSourcesList(
        @Query(PARAMETER_STARTING_AFTER) startingAfter: String?,
        @Query(PARAMETER_ENDING_BEFORE) endingBefore: String?,
        @Query(PARAMETER_LIMIT) limit: Int?
    ): Call<PaymentSourcesListEntity>

    @PUT(UPDATE_PAYMENT_SOURCE)
    fun updatePaymentSource(
        @Path(PAYMENT_SOURCE_ID) paymentSourceId: String,
        @Body request: UpdatePaymentSourceRequest
    ): Call<PaymentSourceEntity>

    @DELETE(DELETE_PAYMENT_SOURCE)
    fun deletePaymentSource(@Path(PAYMENT_SOURCE_ID) paymentSourceId: String): Call<Unit>
}
