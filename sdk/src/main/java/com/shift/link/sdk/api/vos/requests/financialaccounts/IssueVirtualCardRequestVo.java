package com.shift.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;
import com.shift.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Request data to add a new credit/debit card.
 * @author Adrian
 */
public class IssueVirtualCardRequestVo extends UnauthorizedRequestVo {

    public String type = "card";

    /**
     * Virtual card issuer.
     */
    @SerializedName("card_issuer")
    public String cardIssuer;

    public CustodianVo custodian;
}
