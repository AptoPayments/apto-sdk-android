package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Created by pauteruel on 01/03/2018.
 */

public class UpdateFinancialAccountRequestVo extends UnauthorizedRequestVo {
    public String type = "state";

    /**
     * Manage state of a financial account
     */
    @SerializedName("state")
    public String state;
}
