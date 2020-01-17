package com.aptopayments.core.repository.cardapplication.remote

import com.aptopayments.core.network.X_AUTHORIZATION
import com.aptopayments.core.repository.card.remote.entities.CardEntity
import com.aptopayments.core.repository.cardapplication.remote.entities.*
import retrofit2.Call
import retrofit2.http.*

private const val CARD_APPLICATION_ID = "applicationId"
private const val NEW_CARD_APPLICATION_PATH = "v1/user/accounts/apply"
private const val GET_CARD_APPLICATION_PATH = "v1/user/accounts/applications/{applicationId}/status"
private const val CANCEL_CARD_APPLICATION_PATH = "v1/user/accounts/applications/{applicationId}"
private const val SELECT_BALANCE_STORE_PATH = "v1/user/accounts/applications/{applicationId}/select_balance_store"
private const val ACCEPT_DISCLAIMER_PATH = "v1/disclaimers/accept"
private const val ISSUE_CARD_PATH = "v1/user/accounts/issuecard"

internal interface CardApplicationApi {

    @POST(NEW_CARD_APPLICATION_PATH)
    fun startCardApplication(
            @Header(X_AUTHORIZATION) userToken: String,
            @Body applicationRequest: NewCardApplicationRequest
    ): Call<CardApplicationEntity>

    @GET(GET_CARD_APPLICATION_PATH)
    fun getCardApplication(
            @Header(X_AUTHORIZATION) userToken: String,
            @Path(CARD_APPLICATION_ID) cardApplicationId: String
    ): Call<CardApplicationEntity>

    @DELETE(CANCEL_CARD_APPLICATION_PATH)
    fun cancelCardApplication(
            @Header(X_AUTHORIZATION) userToken: String,
            @Path(CARD_APPLICATION_ID) cardApplicationId: String
    ): Call<Unit>

    @POST(SELECT_BALANCE_STORE_PATH)
    fun setBalanceStore(
            @Header(X_AUTHORIZATION) userToken: String,
            @Path(CARD_APPLICATION_ID) cardApplicationId: String,
            @Body request: SelectBalanceStoreRequest
    ): Call<SelectBalanceStoreResultEntity>

    @POST(ACCEPT_DISCLAIMER_PATH)
    fun acceptDisclaimer(
            @Header(X_AUTHORIZATION) userToken: String,
            @Body request: AcceptDisclaimerRequest
    ): Call<Unit>

    @POST(ISSUE_CARD_PATH)
    fun issueCard(
            @Header(X_AUTHORIZATION) userToken: String,
            @Body request: IssueCardRequest
    ): Call<CardEntity>

}
