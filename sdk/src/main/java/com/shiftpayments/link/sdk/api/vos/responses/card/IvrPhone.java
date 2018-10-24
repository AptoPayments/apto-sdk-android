package com.shiftpayments.link.sdk.api.vos.responses.card;

import com.shiftpayments.link.sdk.api.vos.datapoints.PhoneNumberVo;

public class IvrPhone extends PhoneNumberVo {

    public IvrPhone(String countryCode, String phone) {
        super(countryCode+phone, false, false);
    }
}
