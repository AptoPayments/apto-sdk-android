package com.shiftpayments.link.sdk.cardexample;

import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;

public interface InitSdkTaskListener {
    void onConfigRetrieved(ConfigResponseVo result);
}
