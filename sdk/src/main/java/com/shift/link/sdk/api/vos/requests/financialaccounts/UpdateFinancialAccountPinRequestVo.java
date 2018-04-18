package com.shift.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;
import com.shift.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Created by pauteruel on 01/03/2018.
 */

public class UpdateFinancialAccountPinRequestVo extends UnauthorizedRequestVo {
    public String type = "pin";

    /**
     * Manage pin of a financial account
     */
    @SerializedName("pin")
    public String pin;
}