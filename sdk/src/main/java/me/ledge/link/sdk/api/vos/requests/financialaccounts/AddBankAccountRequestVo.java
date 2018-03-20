package me.ledge.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;

import me.ledge.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Request data to add a new bank account.
 * @author Adrian
 */
public class AddBankAccountRequestVo extends UnauthorizedRequestVo {

    public String type = "bank_account";

    /**
     * Token received from Plaid.
     */
    @SerializedName("public_token")
    public String publicToken;

}
