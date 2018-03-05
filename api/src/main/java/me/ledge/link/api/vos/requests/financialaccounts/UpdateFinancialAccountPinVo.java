package me.ledge.link.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;

import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Created by pauteruel on 01/03/2018.
 */

public class UpdateFinancialAccountPinVo extends UnauthorizedRequestVo {
    public String type = "pin";

    /**
     * Manage state of a financial account
     */
    @SerializedName("pin")
    public Card pin;
}
