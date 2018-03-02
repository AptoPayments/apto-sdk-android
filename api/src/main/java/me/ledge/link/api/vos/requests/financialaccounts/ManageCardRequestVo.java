package me.ledge.link.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;

import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Created by pauteruel on 01/03/2018.
 */

public class ManageCardRequestVo extends UnauthorizedRequestVo {
    public String type = "state";

    /**
     * Manage state of a financial account
     */
    @SerializedName("state")
    public Card state;}
