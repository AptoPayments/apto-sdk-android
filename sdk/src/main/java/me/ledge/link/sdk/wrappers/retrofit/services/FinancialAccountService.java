package me.ledge.link.sdk.wrappers.retrofit.services;

import com.google.gson.JsonObject;

import me.ledge.link.sdk.api.vos.datapoints.Card;
import me.ledge.link.sdk.api.vos.datapoints.FinancialAccountVo;
import me.ledge.link.sdk.api.vos.datapoints.VirtualCard;
import me.ledge.link.sdk.api.vos.requests.financialaccounts.AddBankAccountRequestVo;
import me.ledge.link.sdk.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import me.ledge.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountPinRequestVo;
import me.ledge.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountRequestVo;
import me.ledge.link.sdk.api.vos.responses.financialaccounts.TransactionListResponseVo;
import me.ledge.link.sdk.api.vos.responses.financialaccounts.UpdateFinancialAccountPinResponseVo;
import me.ledge.link.sdk.api.vos.responses.financialaccounts.UpdateFinancialAccountResponseVo;
import me.ledge.link.sdk.api.vos.responses.users.UserDataListResponseVo;
import me.ledge.link.sdk.api.vos.responses.verifications.VerificationStatusResponseVo;
import me.ledge.link.sdk.api.wrappers.LinkApiWrapper;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    @POST(LinkApiWrapper.FINANCIAL_ACCOUNTS_PATH)
    Call<VerificationStatusResponseVo> addBankAccount(@Body AddBankAccountRequestVo data);

    /**
     * Creates a {@link Call} to add a credit/debit card.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(LinkApiWrapper.FINANCIAL_ACCOUNTS_PATH)
    Call<Card> addCard(@Body JsonObject data);

    /**
     * Creates a {@link Call} to add a virtual card.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(LinkApiWrapper.ISSUE_CARD_PATH)
    Call<VirtualCard> issueVirtualCard(@Body IssueVirtualCardRequestVo data);

    /**
     * Creates a {@link Call} to get the user's financial accounts.
     * @return API call to execute.
     */
    @GET(LinkApiWrapper.FINANCIAL_ACCOUNTS_PATH)
    Call<UserDataListResponseVo> getFinancialAccounts();

    /**
     * Creates a {@link Call} to get a specific financial account.
     * @return API call to execute.
     */
    @GET(LinkApiWrapper.FINANCIAL_ACCOUNT_PATH)
    Call<FinancialAccountVo> getFinancialAccount(@Path("account_id") String accountId);

    /**
     * Creates a {@link Call} to get a specific financial account.
     * @return API call to execute.
     */

    /** Creates a {@link Call} to change pin card.
     * @param pin Mandatory request data.
     * @return API call to execute.
     */

    @POST(LinkApiWrapper.FINANCIAL_ACCOUNT_PIN_PATH)
    Call<UpdateFinancialAccountPinResponseVo> updateFinancialAccountPin(@Path("account_id") String accountId, @Body UpdateFinancialAccountPinRequestVo pin);

     /** Creates a {@link Call} to manage card status.
     * @param state Mandatory request data.
     * @return API call to execute.
     */

    @POST(LinkApiWrapper.FINANCIAL_ACCOUNT_STATE_PATH)
    Call<UpdateFinancialAccountResponseVo> updateFinancialAccount(@Path("account_id") String accountId, @Body UpdateFinancialAccountRequestVo state);

    /**
     * Creates a {@link Call} to get a the transaction list of specific financial account.
     * @return API call to execute.
     */
    @GET(LinkApiWrapper.FINANCIAL_ACCOUNT_TRANSACTIONS_PATH)
    Call<TransactionListResponseVo> getTransactions(@Path("account_id") String accountId);
}
