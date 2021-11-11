package com.aptopayments.mobile.repository.p2p.remote

import com.aptopayments.mobile.repository.p2p.remote.entities.CardholderDataEntity
import com.aptopayments.mobile.repository.p2p.remote.entities.P2pTransferResponseEntity
import com.aptopayments.mobile.repository.p2p.remote.request.P2pMakeTransferRequest
import retrofit2.Call
import retrofit2.http.*

private const val RECIPIENTS_PATH = "/v1/p2p/recipient"
private const val TRANSFER_PATH = "v1/p2p/transfer"

private const val PARAMENTER_PHONE_COUNTRY_CODE = "phone_country_code"
private const val PARAMENTER_PHONE_NUMBER = "phone_number"
private const val PARAMENTER_EMAIL = "email"

internal interface TransferMoneyApi {

    @GET(RECIPIENTS_PATH)
    fun findRecipient(
        @Query(PARAMENTER_PHONE_COUNTRY_CODE) phoneCountryCode: String?,
        @Query(PARAMENTER_PHONE_NUMBER) phoneNumber: String?,
        @Query(PARAMENTER_EMAIL) email: String?,
    ): Call<CardholderDataEntity>

    @POST(TRANSFER_PATH)
    fun makeTransfer(@Body request: P2pMakeTransferRequest): Call<P2pTransferResponseEntity>
}
