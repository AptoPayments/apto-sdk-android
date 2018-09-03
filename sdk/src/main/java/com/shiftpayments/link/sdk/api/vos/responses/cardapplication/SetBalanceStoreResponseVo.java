package com.shiftpayments.link.sdk.api.vos.responses.cardapplication;

import com.google.gson.annotations.SerializedName;

public class SetBalanceStoreResponseVo {

    public String result;

    @SerializedName("error_code")
    public int errorCode;
}
