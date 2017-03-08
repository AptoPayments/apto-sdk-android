package me.ledge.link.api.wrappers.retrofit.two.services;

import com.google.gson.JsonObject;

import me.ledge.link.api.vos.Card;
import me.ledge.link.api.vos.requests.financialaccounts.AddBankAccountRequestVo;
import me.ledge.link.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import me.ledge.link.api.vos.responses.verifications.VerificationStatusResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
    @POST(LinkApiWrapper.ADD_BANK_ACCOUNTS_PATH)
    Call<VerificationStatusResponseVo> addBankAccount(@Body AddBankAccountRequestVo data);

    /**
     * Creates a {@link Call} to add a credit/debit card.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(LinkApiWrapper.ADD_CARD_PATH)
    Call<Card> addCard(@Body JsonObject data);

    /**
     * Creates a {@link Call} to add a virtual card.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(LinkApiWrapper.ISSUE_VIRTUAL_CARD_PATH)
    Call<Card> issueVirtualCard(@Body IssueVirtualCardRequestVo data);
}
