package me.ledge.link.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;

import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Request data to add a new credit/debit card.
 * @author Adrian
 */
public class IssueVirtualCardRequestVo extends UnauthorizedRequestVo {

    /**
     * Phone number.
     */
    @SerializedName("phone_number")
    public String phoneNumber;

    /**
     * Amount.
     */
    public int amount;

}
