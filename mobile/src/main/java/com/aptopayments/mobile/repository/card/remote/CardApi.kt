package com.aptopayments.mobile.repository.card.remote

import com.aptopayments.mobile.network.PaginatedListEntity
import com.aptopayments.mobile.repository.card.remote.entities.*
import com.aptopayments.mobile.repository.card.remote.entities.ActivatePhysicalCardEntity
import com.aptopayments.mobile.repository.card.remote.entities.CardDetailsEntity
import com.aptopayments.mobile.repository.card.remote.entities.CardEntity
import com.aptopayments.mobile.repository.card.remote.entities.OrderPhysicalCardConfigEntity
import com.aptopayments.mobile.repository.card.remote.entities.ProvisioningEntity
import com.aptopayments.mobile.repository.card.remote.requests.*
import com.aptopayments.mobile.repository.fundingsources.remote.entities.BalanceEntity
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
private const val GPAY_PROVISIONING_PATH = "v1/user/accounts/{account_id}/provision/androidpay"
private const val SET_PASSCODE = "/v1/user/accounts/{account_id}/passcode"
private const val ORDER_PHYSICAL_CONFIG = "/v1/user/accounts/{account_id}/order_physical/config"
private const val ORDER_PHYSICAL = "/v1/user/accounts/{account_id}/order_physical"
private const val ACCOUNT_ID = "account_id"
private const val ACTION = "action"
private const val SHOW_DETAILS = "show_details"
private const val PARAMETER_STARTING_AFTER = "starting_after"
private const val PARAMETER_ENDING_BEFORE = "ending_before"
private const val PARAMETER_LIMIT = "limit"

internal interface CardApi {

    @POST(ISSUE_CARD_PATH)
    fun issueCard(@Body request: IssueCardRequest): Call<CardEntity>

    @GET(FINANCIAL_ACCOUNT_PATH)
    fun getCard(@Path(ACCOUNT_ID) accountID: String, @Query(SHOW_DETAILS) showDetails: Boolean): Call<CardEntity>

    @GET(FINANCIAL_ACCOUNT_PATH)
    fun getCardDetails(
        @Path(ACCOUNT_ID) accountID: String,
        @Query(SHOW_DETAILS) showDetails: Boolean
    ): Call<CardDetailsEntity>

    @GET(FINANCIAL_ACCOUNTS_PATH)
    fun getCards(
        @Query(PARAMETER_STARTING_AFTER) startingAfter: String?,
        @Query(PARAMETER_ENDING_BEFORE) endingBefore: String?,
        @Query(PARAMETER_LIMIT) limit: Int?
    ): Call<PaginatedListEntity<CardEntity>>

    @POST(CHANGE_CARD_STATE_PATH)
    fun changeCardState(
        @Path(ACCOUNT_ID) accountID: String,
        @Path(ACTION) action: String
    ): Call<CardEntity>

    @POST(ACTIVATE_PHYSICAL_CARD_PATH)
    fun activatePhysicalCard(
        @Path(ACCOUNT_ID) accountID: String,
        @Body request: ActivatePhysicalCardRequest
    ): Call<ActivatePhysicalCardEntity>

    @GET(FINANCIAL_ACCOUNT_BALANCE_PATH)
    fun getCardBalance(@Path(ACCOUNT_ID) accountID: String): Call<BalanceEntity>

    @POST(FINANCIAL_ACCOUNT_BALANCE_PATH)
    fun setCardFundingSource(
        @Path(ACCOUNT_ID) accountID: String,
        @Body request: SetCardFundingSourceRequest
    ): Call<BalanceEntity>

    @POST(USER_BALANCES_PATH)
    fun addCardBalance(@Path(ACCOUNT_ID) accountID: String, @Body request: AddCardBalanceRequest): Call<BalanceEntity>

    @POST(FINANCIAL_ACCOUNT_PIN_PATH)
    fun setPin(@Path(ACCOUNT_ID) accountID: String, @Body request: SetPinRequest): Call<CardEntity>

    @POST(GPAY_PROVISIONING_PATH)
    fun getProvisioningOPC(
        @Path(ACCOUNT_ID) accountID: String,
        @Body request: GetProvisioningDataRequestWrapper
    ): Call<ProvisioningEntity>

    @POST(SET_PASSCODE)
    fun setCardPasscode(@Path(ACCOUNT_ID) cardId: String, @Body request: SetPasscodeRequest): Call<Unit>

    @GET(ORDER_PHYSICAL_CONFIG)
    fun getOrderPhysicalCardConfig(@Path(ACCOUNT_ID) cardId: String): Call<OrderPhysicalCardConfigEntity>

    @POST(ORDER_PHYSICAL)
    fun orderPhysicalCard(@Path(ACCOUNT_ID) cardId: String): Call<CardEntity>
}
