package com.shift.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;
import com.shift.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Request data to link a new account to an application.
 * @author Adrian
 */
public class ApplicationAccountRequestVo extends UnauthorizedRequestVo {

    @SerializedName("account_id")
    public String accountId;

    /**
     * Action type.
     * @see me.ledge.link.api.utils.loanapplication.LoanApplicationAccountType
     */
    @SerializedName("account_type")
    public int accountType;
}
