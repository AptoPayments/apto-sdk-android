package com.shiftpayments.link.sdk.api.vos.responses.financialaccounts;

import com.google.gson.annotations.SerializedName;

public class ActivatePhysicalCardResponseVo {

    @SerializedName("result")
    public String result;

    @SerializedName("error_code")
    public int errorCode;

    @SerializedName("error_message")
    public String errorMessage;
}
