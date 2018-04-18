package com.shift.link.sdk.wrappers.retrofit.services;

import com.google.gson.JsonObject;
import com.shift.link.sdk.api.vos.Card;
import com.shift.link.sdk.api.vos.datapoints.FinancialAccountVo;
import com.shift.link.sdk.api.vos.requests.financialaccounts.AddBankAccountRequestVo;
import com.shift.link.sdk.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import com.shift.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountPinRequestVo;
import com.shift.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountRequestVo;
import com.shift.link.sdk.api.vos.responses.financialaccounts.FundingSourceListVo;
import com.shift.link.sdk.api.vos.responses.financialaccounts.FundingSourceVo;
import com.shift.link.sdk.api.vos.responses.financialaccounts.TransactionListResponseVo;
import com.shift.link.sdk.api.vos.responses.financialaccounts.UpdateFinancialAccountPinResponseVo;
import com.shift.link.sdk.api.vos.responses.financialaccounts.UpdateFinancialAccountResponseVo;
import com.shift.link.sdk.api.vos.responses.users.UserDataListResponseVo;
import com.shift.link.sdk.api.vos.responses.verifications.VerificationStatusResponseVo;
import com.shift.link.sdk.api.wrappers.ShiftApiWrapper;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Financial accounts related API calls.
 * @author Wijnand
 */
public interface FinancialAccountService {
    /**
     * Creates a {@link Call} to add a bank account
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(ShiftApiWrapper.FINANCIAL_ACCOUNTS_PATH)
    Call<VerificationStatusResponseVo> addBankAccount(@Body AddBankAccountRequestVo data);

    /**
     * Creates a {@link Call} to add a credit/debit card.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(ShiftApiWrapper.FINANCIAL_ACCOUNTS_PATH)
    Call<Card> addCard(@Body JsonObject data);

    /**
     * Creates a {@link Call} to add a virtual card.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(ShiftApiWrapper.ISSUE_CARD_PATH)
    Call<Card> issueVirtualCard(@Body IssueVirtualCardRequestVo data);

    /**
     * Creates a {@link Call} to get the user's financial accounts.
     * @return API call to execute.
     */
    @GET(ShiftApiWrapper.FINANCIAL_ACCOUNTS_PATH)
    Call<UserDataListResponseVo> getFinancialAccounts();

    /**
     * Creates a {@link Call} to get a specific financial account.
     * @return API call to execute.
     */
    @GET(ShiftApiWrapper.FINANCIAL_ACCOUNT_PATH)
    Call<FinancialAccountVo> getFinancialAccount(@Path("account_id") String accountId);

    /** Creates a {@link Call} to change pin card.
     * @param pin Mandatory request data.
     * @return API call to execute.
     */
    @POST(ShiftApiWrapper.FINANCIAL_ACCOUNT_PIN_PATH)
    Call<UpdateFinancialAccountPinResponseVo> updateFinancialAccountPin(@Path("account_id") String accountId, @Body UpdateFinancialAccountPinRequestVo pin);

     /** Creates a {@link Call} to manage card status.
     * @param state Mandatory request data.
     * @return API call to execute.
     */
    @POST(ShiftApiWrapper.FINANCIAL_ACCOUNT_STATE_PATH)
    Call<UpdateFinancialAccountResponseVo> updateFinancialAccount(@Path("account_id") String accountId, @Body UpdateFinancialAccountRequestVo state);

    /**
     * Creates a {@link Call} to get the transaction list of a specific financial account.
     * @return API call to execute.
     */
    @GET(ShiftApiWrapper.FINANCIAL_ACCOUNT_TRANSACTIONS_PATH)
    Call<TransactionListResponseVo> getTransactions(@Path("account_id") String accountId, @Query("rows") int rows, @Query("last_transaction_id") String lastTransactionId);

    /**
     * Creates a {@link Call} to get a the funding source of a specific financial account.
     * @return API call to execute.
     */
    @GET(ShiftApiWrapper.FINANCIAL_ACCOUNT_FUNDING_SOURCE_PATH)
    Call<FundingSourceVo> getFundingSource(@Path("account_id") String accountId);

    /**
     * Creates a {@link Call} to get the user's funding sources.
     * @return API call to execute.
     */
    @GET(ShiftApiWrapper.USER_FUNDING_SOURCES_PATH)
    Call<FundingSourceListVo> getUserFundingSources();
}
