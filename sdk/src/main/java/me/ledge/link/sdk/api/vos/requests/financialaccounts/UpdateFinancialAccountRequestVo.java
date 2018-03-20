package me.ledge.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pauteruel on 01/03/2018.
 */

public class UpdateFinancialAccountRequestVo extends me.ledge.link.sdk.api.vos.requests.base.UnauthorizedRequestVo {
    public String type = "state";

    /**
     * Manage state of a financial account
     */
    @SerializedName("state")
    public String state;
}
