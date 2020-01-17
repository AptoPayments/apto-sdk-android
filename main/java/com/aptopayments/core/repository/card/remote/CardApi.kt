package com.aptopayments.core.repository.card.remote

import com.aptopayments.core.network.ListEntity
import com.aptopayments.core.network.X_AUTHORIZATION
import com.aptopayments.core.repository.card.remote.entities.ActivatePhysicalCardEntity
import com.aptopayments.core.repository.card.remote.entities.CardDetailsEntity
import com.aptopayments.core.repository.card.remote.entities.CardEntity
import com.aptopayments.core.repository.card.remote.requests.IssueCardRequest
import com.aptopayments.core.repository.card.remote.requests.ActivatePhysicalCardRequest
import com.aptopayments.core.repository.card.remote.requests.AddCardBalanceRequest
import com.aptopayments.core.repository.card.remote.requests.SetCardFundingSourceRequest
import com.aptopayments.core.repository.card.remote.requests.SetPinRequest
import com.aptopayments.core.repository.fundingsources.remote.entities.BalanceEntity
import retrofit2.Call
import retrofit2.http.*

private const val ISSUE_CARD_PATH = "v1/user/accounts/issuecard"
private const val USER_BALANCES_PATH = "v1/user/accounts/{account_id}/balances"
private const val FINANCIAL_ACCOUNTS_PATH = "v1/user/accounts"
private const val FINANCIAL_ACCOUNT_PATH = "v1/user/accounts/{account_id}"
private const val FINANCIAL_ACCOUNT_BALANCE_PATH = "v1/user/accounts/{account_id}/balance"
private const val FINANCIAL_ACCOUNT_PIN_PATH = "v1/user/accounts/{account_id}/pin"
private const val CHANGE_CARD_STATE_PATH = "v1/user/accounts/{account_id}/{action}"
private const val ACTIVATE_PHYSICAL_CARD_PATH = "v1/user/accounts/{account_id}/activate_physical"
private const val ACCOUNT_ID = "account_id"
private const val ACTION = "action"
private const val SHOW_DETAILS = "show_details"

internal interface CardApi {

    @POST(ISSUE_CARD_PATH)
    fun issueCard(
            @Header(X_AUTHORIZATION) userToken: String,
            @Body request: IssueCardRequest
    ): Call<CardEntity>

    @GET(FINANCIAL_ACCOUNT_PATH)
    fun getCard(
            @Header(X_AUTHORIZATION) userToken: String,
            @Path(ACCOUNT_ID) accountID: String,
            @Query(SHOW_DETAILS) showDetails: Boolean
    ): Call<CardEntity>

    @GET(FINANCIAL_ACCOUNT_PATH)
    fun getCardDetails(
            @Header(X_AUTHORIZATION) userToken: String,
            @Path(ACCOUNT_ID) accountID: String,
            @Query(SHOW_DETAILS) showDetails: Boolean
    ): Call<CardDetailsEntity>

    @GET(FINANCIAL_ACCOUNTS_PATH)
    fun getCards(
            @Header(X_AUTHORIZATION) userToken: String
    ): Call<ListEntity<CardEntity>>

    @POST(CHANGE_CARD_STATE_PATH)
    fun changeCardState(
            @Header(X_AUTHORIZATION) userToken: String,
            @Path(ACCOUNT_ID) accountID: String,
            @Path(ACTION) action: String
    ): Call<CardEntity>

    @POST(ACTIVATE_PHYSICAL_CARD_PATH)
    fun activatePhysicalCard(
            @Header(X_AUTHORIZATION) userToken: String,
            @Path(ACCOUNT_ID) accountID: String,
            @Body request: ActivatePhysicalCardRequest
    ): Call<ActivatePhysicalCardEntity>

    @GET(FINANCIAL_ACCOUNT_BALANCE_PATH)
    fun getCardBalance(
            @Header(X_AUTHORIZATION) userToken: String,
            @Path(ACCOUNT_ID) accountID: String
    ): Call<BalanceEntity>

    @POST(FINANCIAL_ACCOUNT_BALANCE_PATH)
    fun setCardFundingSource(
            @Header(X_AUTHORIZATION) userToken: String,
            @Path(ACCOUNT_ID) accountID: String,
            @Body request: SetCardFundingSourceRequest
    ): Call<BalanceEntity>

    @POST(USER_BALANCES_PATH)
    fun addCardBalance(
            @Header(X_AUTHORIZATION) userToken: String,
            @Path(ACCOUNT_ID) accountID: String,
            @Body request: AddCardBalanceRequest
    ): Call<BalanceEntity>

    @POST(FINANCIAL_ACCOUNT_PIN_PATH)
    fun setPin(
            @Header(X_AUTHORIZATION) userToken: String,
            @Path(ACCOUNT_ID) accountID: String,
            @Body request: SetPinRequest
    ): Call<CardEntity>
}
